import type { UserApiResponse } from "../../models/users/Api/UserApiResponse";
import { request } from "../general/request";
import { loginRoute } from "../../config/apiRoutes";
import type { Response } from "../../models/api/Response";
import type { User } from "../../models/users/Local/User";
import { fromUserResponseToUser } from "../../parsers/user";
import type { LoginDto } from "../../models/users/Api/LoginApi";

export async function logIn(LoginDto: LoginDto): Promise<Response<User>>{
    if(!LoginDto.email || !LoginDto.password) return { success: null, error: {code: "UNFILLED_FIELDS", message: "Por favor, llene todos los campos"}};

    const response = await request<UserApiResponse>({ method: "POST", url: loginRoute,data: LoginDto})
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