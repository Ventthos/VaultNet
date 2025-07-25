import type { UserApiResponse } from "../models/users/Api/UserApiResponse";
import type { User } from "../models/users/Local/User";

export function fromUserResponseToUser(userApiResponse:UserApiResponse):User{
    return {
        id: userApiResponse.id,
        name: userApiResponse.name,
        paternalLastname: userApiResponse.paternalLastname,
        maternalLastname: userApiResponse.maternalLastname,
        username: userApiResponse.username,
        email: userApiResponse.email,
        imageUrl: userApiResponse.image
    }
}