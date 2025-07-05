package com.ventthos.Vaultnet.dto.user;


public record UserResponseDto (
    Long id,
    String name,
    String paternalLastname,
    String maternalLastname,
    String email
){}