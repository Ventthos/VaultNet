package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.Category;
import com.ventthos.Vaultnet.dto.category.CategoryResponseDto;
import com.ventthos.Vaultnet.dto.category.CreateCategoryDto;
import com.ventthos.Vaultnet.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BusinessService businessService;

    public CategoryService(CategoryRepository categoryRepository, BusinessService businessService){
        this.categoryRepository = categoryRepository;
        this.businessService = businessService;
    }

    public CategoryResponseDto createCategory(CreateCategoryDto newCategory){
        // Obtenemos el business
        Business business = businessService.getBusinessOrTrow(newCategory.businessId());

        // Formamos el objeto
        Category category = new Category();
        category.setName(newCategory.name());
        category.setColor(newCategory.color());
        category.setBusiness(business);

        // Lo guardamos
        Category savedCategory = categoryRepository.save(category);

        // Actualizamos la lista de categorias del business
        business.getCategories().add(savedCategory);

        return new CategoryResponseDto(
                savedCategory.getCategoryId(),
                category.getName(),
                category.getColor(),
                category.getBusiness().getBusinessId()
        );
    }
}
