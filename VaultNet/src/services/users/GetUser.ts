import { request } from "../general/request";
import { userRoute } from "../../config/apiRoutes";
import type { UserApiResponse } from "../../models/users/Api/UserApiResponse";
import type { Response } from "../../models/api/Response";
import type { User } from "../../models/users/Local/User";
import { fromUserResponseToUser } from "../../parsers/user";

export async function getUser(email:string, token:string): Promise<Response<User[]>>{
    const response = await request<UserApiResponse[]>({method: "GET", url: userRoute, token, params: {email:email}})
    if(!response.success) return { success: null, error: response.error };

    return{
        success: {
            code: response.success?.code,
            data: response.success.data.map((user)=>fromUserResponseToUser(user)),
        },
        error: null
    }
}