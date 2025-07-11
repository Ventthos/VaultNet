package com.ventthos.Vaultnet.parsers;

import com.ventthos.Vaultnet.domain.Category;
import com.ventthos.Vaultnet.dto.category.CategoryResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryParser {
    public CategoryResponseDto toCategoryDto(Long id, Category category){
        return new CategoryResponseDto(
                category.getCategoryId(),
                category.getName(),
                category.getColor(),
                category.getBusiness().getBusinessId()
        );
    }
}
