package com.ventthos.Vaultnet.dto.responses;

public record ApiResponse<T>(
        String status,
        String message,
        T data
) {}