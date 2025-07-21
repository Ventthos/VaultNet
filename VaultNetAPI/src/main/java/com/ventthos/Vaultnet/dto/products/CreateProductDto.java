package com.ventthos.Vaultnet.dto.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateProductDto(
        @NotBlank(message = "Se necesita el campo name")
        @Length(max = 100, message = "El campo name no puede tener más de 100 caracteres")
        String name,
        String description,
        @Min(value = 0, message = "La cantidad no puede ser menor a 0")
        Integer quantity,
        @Min(value = 0, message = "La cantidad mínima no puede ser menor a 0")
        Integer minQuantity,
        @Min(value = 0, message = "La cantidad para el aviso no puede ser menor a 0")
        Integer alertQuantity,
        @NotNull(message = "Se necesita el campo unitId")
        Long unitId,
        @NotNull(message = "Se necesita el campo categoryId")
        Long categoryId
) {
}
