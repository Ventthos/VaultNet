package com.ventthos.Vaultnet.dto.responses;

public record ApiResponseWithToken<T>(
        String status,
        String code,
        String message,
        T data,
        String token
) {}