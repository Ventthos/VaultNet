package com.ventthos.Vaultnet.controller;

import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.responses.ApiResponse;
import com.ventthos.Vaultnet.dto.user.LoginUserDto;
import com.ventthos.Vaultnet.dto.user.RegisterUserDto;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;
import com.ventthos.Vaultnet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> RegisterUser(@RequestBody @Valid RegisterUserDto newUser){
        try {
            UserResponseDto userResponseDto = userService.registerUser(newUser);

            // Construir la URI del nuevo recurso
            URI location = URI.create("/user/" + userResponseDto.id());

            return ResponseEntity
                    .created(location)
                    .body(new ApiResponse<>("Success", "Usuario creado exitosamente", userResponseDto));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("Error", e.getMessage(), null)
            );
        }
    }

    @PostMapping("auth/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> Login(@RequestBody @Valid LoginUserDto loginUser){
        try {
            UserResponseDto user = userService.loginUser(loginUser);
            return ResponseEntity.ok(
                    new ApiResponse<>("Success", "Sesión iniciada correctamente", user)
            );
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(
                    new ApiResponse<>("Error", e.getMessage(), null)
            );
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(
                    new ApiResponse<>("Ocurrió un error inesperado al iniciar sesión", e.getMessage(), null)
            );
        }

    }
}
