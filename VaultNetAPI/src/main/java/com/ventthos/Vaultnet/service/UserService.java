package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.config.SecurityConfig;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.user.*;
import com.ventthos.Vaultnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Se necesita del user repository y security config para poder acceder a los usuarios
    // y también para poder encriptar la contraseña
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto registerUser(RegisterUserDto newUser) throws IllegalArgumentException{
        // No hay users con el mismo email
        if(userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Un usuario con ese email ya se encuentra registrado");
        }// Ni users con contraseñas menores a seis caracteres
        else if(newUser.getPassword().length() < 6){
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
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
            return new UserResponseDto(id,newParsedUser.getName(), newParsedUser.getPaternalLastName(),
                    newParsedUser.getMaternalLastName(), newParsedUser.getEmail());

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No se pudo guardar el elemento");
        }
    }

    public UserResponseDto loginUser(LoginUserDto loginUser) throws IllegalArgumentException{
        // Vemos si existe el usuario
        Optional<User> userInRepo = userRepository.findByEmail(loginUser.email());
        if(userInRepo.isEmpty()){
            throw new IllegalArgumentException("No existe una cuenta con el correo proporcionado.");
        }
        // Vemos si la contraseña coincide
        if(!passwordEncoder.matches(loginUser.password(), userInRepo.get().getPassword())){
            throw new IllegalArgumentException("La contraseña proporcionada no coincide.");
        }
        // Obtenemos el usuario
        User loggedUser = userInRepo.get();
        return new UserResponseDto(loggedUser.getUserId(),loggedUser.getName(), loggedUser.getPaternalLastName(),
                loggedUser.getMaternalLastName(), loggedUser.getEmail());
    }

    public UserResponseDto GetUserById(Long id) throws IllegalArgumentException{
        // Vemos si existe el usuario
        Optional<User> userInRepo = userRepository.findById(id);
        // Si no existe mandamos error
        if(userInRepo.isEmpty()){
            throw new IllegalArgumentException("No existe un usuario con el ID proporcionado");
        }
        // Obtenemos el usuario si existe
        User loggedUser = userInRepo.get();
        return new UserResponseDto(loggedUser.getUserId(),loggedUser.getName(), loggedUser.getPaternalLastName(),
                loggedUser.getMaternalLastName(), loggedUser.getEmail());
    }

    public UserBusinessesResponse getUserBusinesses(Long id) throws IllegalArgumentException{
        // Vemos si existe el usuario
        Optional<User> userInRepo = userRepository.findById(id);
        // Si no existe mandamos error
        if(userInRepo.isEmpty()){
            throw new IllegalArgumentException("No existe un usuario con el ID proporcionado");
        }

        // Obtenemos el usuario existente
        User user = userInRepo.get();
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
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        return userOptional.get();
    }

    public void validateUserBelongsToBusiness(Long userId, Long businessId) throws IllegalAccessException {
        if(getUserBusinesses(userId).businesses().stream()
                .noneMatch(userBusinessInResponse -> userBusinessInResponse.businessId().equals(businessId)))
        {
            throw new IllegalAccessException("El usuario no pertenece al negocio con id " + businessId);
        }
    }
}
