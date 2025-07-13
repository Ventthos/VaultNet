package com.ventthos.Vaultnet.dto.responses;

public record ApiResponse<T>(
        String status,
        String code,
        String message,
        T data
) {}