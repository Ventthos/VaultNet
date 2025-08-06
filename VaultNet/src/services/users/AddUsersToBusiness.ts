import { request } from "../general/request";
import { businessRoute } from "../../config/apiRoutes";
import type { UserApiResponse } from "../../models/users/Api/UserApiResponse";
import { fromUserResponseToUser } from "../../parsers/user";


export async function addUsersToBusiness(emails: string[], businessId: number, token:string){
    const url = businessRoute + `/${businessId}/members`
    const response = await request<UserApiResponse[]>({method: "POST", url, token, data: {emails}})

    if(!response.success) return { success: null, error: response.error };

    return{
            success: {
                code: response.success?.code,
                data: response.success.data.map((user)=>fromUserResponseToUser(user)),
            },
            error: null
    }
}