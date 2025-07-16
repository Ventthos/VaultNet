package com.ventthos.Vaultnet.dto.change;

import com.ventthos.Vaultnet.domain.Product;
import com.ventthos.Vaultnet.domain.User;

public record CreateChangeDto(
         Product product,
         User user,
         String oldValuesJson,
         String newValuesJson
) {
}
