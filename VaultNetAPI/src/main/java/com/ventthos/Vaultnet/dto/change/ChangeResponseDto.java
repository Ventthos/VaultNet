package com.ventthos.Vaultnet.dto.change;

import com.ventthos.Vaultnet.domain.Product;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;

public record ChangeResponseDto(
        Long changeId,
        Long productId,
        UserResponseDto user,
        String oldValuesJson,
        String newValuesJson
) {
}
