package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.config.SecurityConfig;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.user.*;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.parsers.UserParser;
import com.ventthos.Vaultnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserParser userParser;

    // Se necesita del user repository y security config para poder acceder a los usuarios
    // y también para poder encriptar la contraseña
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserParser userParser){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userParser = userParser;
    }

    public UserResponseDto registerUser(RegisterUserDto newUser) throws IllegalArgumentException{
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
        newParsedUser.setEmail(newUser.getEmail());
        newParsedUser.setPassword(hashedPassword);

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

    public User getUserOrTrow(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new ApiException(Code.USER_NOT_FOUND);
        }

        return userOptional.get();
    }

    public void validateUserBelongsToBusiness(Long userId, Long businessId) {
        if(getUserBusinesses(userId).businesses().stream()
                .noneMatch(userBusinessInResponse -> userBusinessInResponse.businessId().equals(businessId)))
        {
            throw new ApiException(Code.ACCESS_DENIED);
        }
    }
}
