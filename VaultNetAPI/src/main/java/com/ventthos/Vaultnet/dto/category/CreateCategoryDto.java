package com.ventthos.Vaultnet.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCategoryDto(
        @NotBlank(message = "Se necesita del campo name")
        String name,

        @NotBlank(message = "Se necesita el campo color")
        @Pattern(
                regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$",
                message = "El color debe estar en formato hexadecimal, por ejemplo: #FF5733"
        )
        String color,

        @NotBlank(message = "Se necesita el campo businessId")
        Long businessId
) {
}
