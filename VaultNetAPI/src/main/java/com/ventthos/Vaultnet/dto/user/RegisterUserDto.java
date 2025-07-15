package com.ventthos.Vaultnet.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterUserDto {

    @NotBlank(message = "No se acepta el campo 'name' vacío")
    private String name;

    @NotBlank(message = "El apellido paterno es obligatorio")
    private String paternalLastname;

    @NotBlank(message = "El apellido materno es obligatorio")
    private String maternalLastname;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}