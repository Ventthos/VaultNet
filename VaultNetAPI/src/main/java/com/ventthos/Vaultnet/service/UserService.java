package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.config.FileRoutes;
import com.ventthos.Vaultnet.config.FileStorageService;
import com.ventthos.Vaultnet.config.SecurityConfig;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.user.*;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.parsers.UserParser;
import com.ventthos.Vaultnet.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserParser userParser;
    private final FileStorageService fileStorageService;

    // Se necesita del user repository y security config para poder acceder a los usuarios
    // y también para poder encriptar la contraseña
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserParser userParser,
                       FileStorageService fileStorageService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userParser = userParser;
        this.fileStorageService = fileStorageService;
    }

    public UserResponseDto registerUser(RegisterUserDto newUser, MultipartFile imageFile) throws IllegalArgumentException{
        // Se sube la imagen
        String imagePath = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            // Guardar la imagen
            imagePath = fileStorageService.save(imageFile, FileRoutes.USERS);
        }

        // No hay users con el mismo email
        if(userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new ApiException(Code.EMAIL_ALREADY_REGISTERED);
        }// Ni users con contraseñas menores a seis caracteres
        else if(newUser.getPassword().length() < 6){
            throw new ApiException(Code.INVALID_PASSWORD);
        }

        // Hasheamos la contraseña por seguridad
        String hashedPassword = passwordEncoder.encode(newUser.getPassword());

        // Parseamos el user a guardar
        User newParsedUser = new User();
        newParsedUser.setName(newUser.getName());
        newParsedUser.setPaternalLastName(newUser.getPaternalLastname());
        newParsedUser.setMaternalLastName(newUser.getMaternalLastname());
        newParsedUser.setUsername(newUser.getUsername());
        newParsedUser.setEmail(newUser.getEmail());
        newParsedUser.setPassword(hashedPassword);
        newParsedUser.setImage(imagePath);

        // Lo guardamos y obtenemos el id
        try {
            Long id = userRepository.save(newParsedUser).getUserId();

            // Devolvemos el nuevo user
            return userParser.toUserResponseDto(id, newParsedUser);

        } catch (IllegalArgumentException e) {
            throw new ApiException(Code.CAN_NOT_SAVE);
        }
    }

    public UserResponseDto loginUser(LoginUserDto loginUser) throws IllegalArgumentException{
        // Vemos si existe el usuario
        Optional<User> userInRepo = userRepository.findByEmail(loginUser.email());
        if(userInRepo.isEmpty()){
            throw new ApiException(Code.USER_NOT_FOUND);
        }
        // Vemos si la contraseña coincide
        if(!passwordEncoder.matches(loginUser.password(), userInRepo.get().getPassword())){
            throw new ApiException(Code.INVALID_CREDENTIALS);
        }
        // Obtenemos el usuario
        User loggedUser = userInRepo.get();
        return userParser.toUserResponseDto(loggedUser.getUserId(), loggedUser);
    }


    public UserResponseDto GetUserById(Long id) throws IllegalArgumentException{
        // Obtenemos el usuario si existe
        User loggedUser = getUserOrTrow(id);
        return userParser.toUserResponseDto(id, loggedUser);
    }

    @Transactional
    public UserBusinessesResponse getUserBusinesses(Long id) throws IllegalArgumentException{

        // Obtenemos el usuario existente
        User user = getUserOrTrow(id);
        return new UserBusinessesResponse(
                id,
                user.getBusinesses().stream().map(userBusiness -> new UserBusinessInResponse(
                        userBusiness.getBusiness().getBusinessId(),
                        userBusiness.getBusiness().getName(),
                        userBusiness.getBusiness().getLogoUrl()
                )).toList()
        );
    }

    @Transactional
    public User getUserOrTrow(Long userId){
        return userRepository.findByIdWithBusinesses(userId)
                .orElseThrow(() -> new ApiException(Code.USER_NOT_FOUND));
    }

    public User getUserOrTrow(String userEmail){
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        if(userOptional.isEmpty()){
            throw new ApiException(Code.USER_NOT_FOUND);
        }

        return userOptional.get();
    }

    @Transactional
    public void validateUserBelongsToBusiness(Long userId, Long businessId) {
        if(getUserBusinesses(userId).businesses().stream()
                .noneMatch(userBusinessInResponse -> userBusinessInResponse.businessId().equals(businessId)))
        {
            throw new ApiException(Code.ACCESS_DENIED);
        }
    }

    public List<UserResponseDto> findUsersWithEmailLike(String email){
        List<User> usersFound = userRepository.findByEmailContainingIgnoreCase(email);
        return usersFound.stream().map(user -> userParser.toUserResponseDto(user.getUserId(), user)).toList();
    }
}
