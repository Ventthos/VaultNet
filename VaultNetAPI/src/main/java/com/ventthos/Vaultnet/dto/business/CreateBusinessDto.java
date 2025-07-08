package com.ventthos.Vaultnet.dto.business;

import jakarta.validation.constraints.NotBlank;

public record CreateBusinessDto (
        @NotBlank
        String name,

        String logoUrl
){

}
