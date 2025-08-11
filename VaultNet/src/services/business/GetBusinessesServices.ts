import { request } from "../general/request";
import { userBusinessesRoute } from "../../config/apiRoutes";
import type { BusinessResponse } from "../../models/business/api/BusinessResponse";
import { fromBusinessResponseToBusiness } from "../../parsers/BusinessParser";

export async function getBusinessesFromUser( token:string){
    const response = await request<BusinessResponse[]>({method:"GET", url:userBusinessesRoute, token:token})
    if(response.error) return { success: null, error: response.error };

    return{
        error: null,
        success:{
            code: response.success?.code!,
            data: response.success?.data!.map((business)=> fromBusinessResponseToBusiness(business))!
        }
    }
}