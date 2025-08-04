import type { UserApiResponse } from "../../users/Api/UserApiResponse";

export interface BusinessResponse {
    id: number,
    name: string,
    logoUrl: string,
    owner: UserApiResponse,
    users: UserApiResponse[]
}