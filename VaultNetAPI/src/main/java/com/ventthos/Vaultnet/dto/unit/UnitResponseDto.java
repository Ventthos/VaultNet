package com.ventthos.Vaultnet.dto.unit;

public record UnitResponseDto(
        Long unitDto,
        String name,
        String symbol,
        Long businessId
) {
}
