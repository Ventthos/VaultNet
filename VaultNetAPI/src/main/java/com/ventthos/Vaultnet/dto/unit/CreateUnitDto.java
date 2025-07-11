package com.ventthos.Vaultnet.dto.unit;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CreateUnitDto(
        @NotBlank(message = "El campo name no puede estar vacío")
        String name,
        @NotBlank(message = "El campo symbol no puede estar vacío")
        @Length(max = 10, message = "El símbolo no puede llevar más de 10 letras.")
        String symbol
) {
}
