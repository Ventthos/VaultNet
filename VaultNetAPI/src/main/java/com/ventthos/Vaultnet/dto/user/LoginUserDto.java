package com.ventthos.Vaultnet.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(
        @Email(message = "Proporcione un email válido")
        @NotBlank(message = "No se acepta el campo 'email' vacío")
        String email,

        @NotBlank(message = "No se acepta el campo 'password' vacío")
        String password
){ }
