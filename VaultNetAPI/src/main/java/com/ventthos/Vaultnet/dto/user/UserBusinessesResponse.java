package com.ventthos.Vaultnet.dto.user;

import java.util.List;

public record UserBusinessesResponse(
        Long userId,
        List<UserBusinessInResponse> businesses
) {

}