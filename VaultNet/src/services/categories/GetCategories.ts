import { request } from "../general/request";
import { businessRoute } from "../../config/apiRoutes";
import { fromCategoryDtoToCategory } from "../../parsers/CategoryParser";
import type { CategoryDto } from "../../models/category/Api/CategoryDto";

export async function getCategories(businessId: number, token:string) {
    const response = await request<CategoryDto[]>({method:"GET", url:`${businessRoute}/${businessId}/categories`, token})
    if(!response.success) return { success: null, error: response.error };

    return{
        error: null,
        success: {
            code: response.success?.code,
            data: response.success.data.map(category => fromCategoryDtoToCategory(category))
        }
    }

}