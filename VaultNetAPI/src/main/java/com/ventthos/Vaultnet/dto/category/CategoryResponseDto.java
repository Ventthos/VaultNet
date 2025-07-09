package com.ventthos.Vaultnet.dto.category;

public record CategoryResponseDto (
        Long categoryId,
        String name,
        String color,
        Long businessId
){
}
