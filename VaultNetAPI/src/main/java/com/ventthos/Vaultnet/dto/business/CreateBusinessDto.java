package com.ventthos.Vaultnet.dto.business;

import jakarta.validation.constraints.NotBlank;

public record CreateBusinessDto (
        @NotBlank(message = "Se requiere el campo name")
        String name,

        String logoUrl
){

}
