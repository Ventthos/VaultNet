package com.ventthos.Vaultnet.dto.products;

public record PatchProductDto(
        String name,
        String description,
        Integer quantity,
        Integer minQuantity,
        Integer alertQuantity,
        Long unitId,
        Long categoryId
) { }
