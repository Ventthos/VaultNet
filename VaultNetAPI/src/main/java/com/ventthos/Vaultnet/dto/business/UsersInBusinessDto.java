package com.ventthos.Vaultnet.dto.business;

import com.ventthos.Vaultnet.dto.user.UserResponseDto;

import java.util.List;

public record UsersInBusinessDto(
        List<UserResponseDto> usersEmails
) {
}
