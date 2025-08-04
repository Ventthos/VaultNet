import type { BusinessResponse } from "../../models/business/api/BusinessResponse";
import { request } from "../general/request";
import { businessRoute } from "../../config/apiRoutes";
import { fromBusinessResponseToBusiness } from "../../parsers/BusinessParser";
import type { Response } from "../../models/api/Response";
import type { Business } from "../../models/business/local/Business";

export async function CreateBusinessService(createBusinessDto: FormData, token:string): Promise<Response<Business>>{
    for(const pair of createBusinessDto.entries()){
        if(!pair[1]) return { success: null, error: {code: "UNFILLED_FIELDS", message: "Por favor, llene todos los campos"}};
    }

    const response = await request<BusinessResponse>({method:"POST", url:businessRoute, data:createBusinessDto, token})
    if(response.error) return { success: null, error: response.error };

    return {
            error: null,
            success:{
                code: response.success?.code!,
                data: fromBusinessResponseToBusiness(response.success?.data!),
            }
        }
}