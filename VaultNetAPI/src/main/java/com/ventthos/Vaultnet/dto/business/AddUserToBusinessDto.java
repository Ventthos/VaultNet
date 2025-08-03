package com.ventthos.Vaultnet.dto.business;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AddUserToBusinessDto(
        @NotNull
        List<@Email String> usersEmails
        ) {

}
