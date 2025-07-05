package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.config.SecurityConfig;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.user.LoginUserDto;
import com.ventthos.Vaultnet.dto.user.RegisterUserDto;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;
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
        User newParsedUser = User.builder()
                .name(newUser.getName())
                .paternalLastName(newUser.getPaternalLastname())
                .maternalLastName(newUser.getMaternalLastname())
                .email(newUser.getEmail())
                .password(hashedPassword)
                .build();

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
        Optional<User> userInRepo = userRepository.findByEmail(loginUser.email());
        if(userInRepo.isEmpty()){
            throw new IllegalArgumentException("No existe una cuenta con el correo proporcionado.");
        }
        if(!passwordEncoder.matches(loginUser.password(), userInRepo.get().getPassword())){
            throw new IllegalArgumentException("La contraseña proporcionada no coincide.");
        }
        User loggedUser = userInRepo.get();
        return new UserResponseDto(loggedUser.getUserId(),loggedUser.getName(), loggedUser.getPaternalLastName(),
                loggedUser.getMaternalLastName(), loggedUser.getEmail());
    }
}
