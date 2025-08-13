import type { CategoryDto } from "../../models/category/Api/CategoryDto";
import type { Category } from "../../models/category/Local/Category";
import { request } from "../general/request";
import { businessRoute } from "../../config/apiRoutes";
import { fromCategoryDtoToCategory } from "../../parsers/CategoryParser";

export async function createCategory(newCategory: Category, businessId: number, token:string){
    if(newCategory.color === undefined || newCategory.color === null){
        return { success: null, error: {code: "UNFILLED_FIELDS", message: "Por favor, llene todos los campos"}}
    } 

    const response = await request<CategoryDto>({method:"POST", url:`${businessRoute}/${businessId}/categories`, 
        data:newCategory, token})
    if(!response.success) return { success: null, error: response.error };

    return {
        error: null,
        success: {
            code: response.success?.code,
            data: fromCategoryDtoToCategory(response.success.data)
        }
    }
}