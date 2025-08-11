package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.Category;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.category.CategoryResponseDto;
import com.ventthos.Vaultnet.dto.category.CreateCategoryDto;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.parsers.CategoryParser;
import com.ventthos.Vaultnet.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final BusinessService businessService;
    private final CategoryParser categoryParser;

    public CategoryService(CategoryRepository categoryRepository, BusinessService businessService, CategoryParser categoryParser){
        this.categoryRepository = categoryRepository;
        this.businessService = businessService;
        this.categoryParser = categoryParser;
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



        return categoryParser.toCategoryDto(savedCategory.getCategoryId(), category);

    }

    public List<CategoryResponseDto> getCategoriesFromBusiness(Long businessId){
        // Obtenemos el business
        Business business = businessService.getBusinessOrTrow(businessId);

        // Parseamos cada una de las categorias
        return business.getCategories().stream().map(category ->
             categoryParser.toCategoryDto(category.getCategoryId(), category)
        ).toList();
    }

    public Category getCategoryOrThrow(Long categoryId){
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty()){
            throw new ApiException(Code.CATEGORY_NOT_FOUND);
        }
        return categoryOptional.get();
    }

    public void confirmCategoryIsFromBusinessOrTrow(Long businessId, Long categoryId) throws IllegalAccessException{
        Category category = getCategoryOrThrow(categoryId);

        if(!category.getBusiness().getBusinessId().equals(businessId)){
            throw new ApiException(Code.ACCESS_DENIED);
        }
    }

    public CategoryResponseDto getCategory(Long categoryId){

        Category category = getCategoryOrThrow(categoryId);

        return categoryParser.toCategoryDto(category.getCategoryId(), category);
    }
}
