package com.ventthos.Vaultnet.dto.products;

public record PatchProductDto(
        String name,
        String description,
        Integer quantity,
        Long unitId,
        Long categoryId
) { }
