import { request } from "../general/request";
import { verifyTokenRoute } from "../../config/apiRoutes";

export async function verifyToken(token:string){
    const response = await request<boolean>({method: "GET", url:verifyTokenRoute, token})
    if(response.error) return { success: null, error: response.error };

    return {
            error: null,
            success:{
                code: response.success?.code!,
                data: response.success?.data,
                token: response.success?.token
            }
        }
}