import { registerRoute } from "../../config/apiRoutes";
import type { Response } from "../../models/api/Response";
import type { UserApiResponse } from "../../models/users/Api/UserApiResponse";
import type { User } from "../../models/users/Local/User";
import { fromUserResponseToUser } from "../../parsers/user";
import { request } from "../general/request";


export async function register(registerDto: FormData): Promise<Response<User>>{
    for(const pair of registerDto.entries()){
        if(!pair[1]) return { success: null, error: {code: "UNFILLED_FIELDS", message: "Por favor, llene todos los campos"}};
        if(pair[0] === "password" && pair[1].toString().length < 6) return { success: null, error: {code: "INVALID_PASSWORD", message: "La contraseña debe tener al menos 6 caracteres"}};
    }

    const response = await request<UserApiResponse>({ method: "POST", url: registerRoute,data: registerDto})
    if(response.error) return { success: null, error: response.error };

    return {
        error: null,
        success:{
            code: response.success?.code!,
            data: fromUserResponseToUser(response.success?.data!),
            token: response.success?.token
        }
    }
}