package com.ventthos.Vaultnet.dto.products;

import com.ventthos.Vaultnet.dto.category.CategoryResponseDto;
import com.ventthos.Vaultnet.dto.unit.UnitResponseDto;

public record ProductResponseDto (
   Long productId,
   Long businessId,
   String name,
   String description,
   String image,
   Integer quantity,
   Integer minQuantity,
   Integer alertQuantity,
   UnitResponseDto unit,
   CategoryResponseDto category
){ }
