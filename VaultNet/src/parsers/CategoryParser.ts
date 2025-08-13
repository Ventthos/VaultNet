import type { CategoryDto } from "../models/category/Api/CategoryDto";
import type { Category } from "../models/category/Local/Category";

export function fromCategoryDtoToCategory(categoryDto:CategoryDto): Category{
    return{
        id: categoryDto.categoryId,
        name: categoryDto.name,
        color: categoryDto.color,
        businessId: categoryDto.businessId
    }
}

export function fromCategoryToCategoryDto(category:Category): CategoryDto{
    return{
        categoryId: category.id,
        name: category.name,
        color: category.color,
        businessId: category.businessId
    }
}