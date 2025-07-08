package com.ventthos.Vaultnet.dto.business;

import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.domain.UserBusiness;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record BusinessResponseDto (
        Long id,
        String name,
        String logoUrl,
        UserResponseDto owner,
        List<UserResponseDto> users){
}

