package com.ventthos.Vaultnet.dto.business;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateBusinessDto (
        @NotBlank(message = "Se requiere el campo name")
        String name,
        List< @Email(message = "Formato de correo inválido en la lista de usuarios")  String> membersEmails
){

}
