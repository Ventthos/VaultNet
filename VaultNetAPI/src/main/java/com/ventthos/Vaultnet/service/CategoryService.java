package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.Category;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.category.CategoryResponseDto;
import com.ventthos.Vaultnet.dto.category.CreateCategoryDto;
import com.ventthos.Vaultnet.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BusinessService businessService;

    public CategoryService(CategoryRepository categoryRepository, BusinessService businessService){
        this.categoryRepository = categoryRepository;
        this.businessService = businessService;
    }

    public CategoryResponseDto createCategory(CreateCategoryDto newCategory, Long businessId){
        // Obtenemos el business
        Business business = businessService.getBusinessOrTrow(businessId);

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

    public List<CategoryResponseDto> getCategoriesFromBusiness(Long businessId){
        // Obtenemos el business
        Business business = businessService.getBusinessOrTrow(businessId);

        // Parseamos cada una de las categorias
        return business.getCategories().stream().map(category ->
             new CategoryResponseDto(
                     category.getCategoryId(),
                    category.getName(),
                    category.getColor(),
                    category.getBusiness().getBusinessId())
        ).toList();
    }

    public void confirmCategoryIsFromBusinessOrTrow(Long businessId, Long categoryId) throws IllegalAccessException{
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty()){
            throw new IllegalArgumentException("Categoría no encontrada");
        }

        if(!categoryOptional.get().getBusiness().getBusinessId().equals(businessId)){
            throw new IllegalAccessException("No se tiene permiso para ver esta categoria");
        }
    }

    public CategoryResponseDto getCategory(Long categoryId){
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty()){
            throw new IllegalArgumentException("Categoría no encontrada");
        }

        Category category = categoryOptional.get();

        return new CategoryResponseDto(
                category.getCategoryId(),
                category.getName(),
                category.getColor(),
                category.getBusiness().getBusinessId()
        );
    }
}
