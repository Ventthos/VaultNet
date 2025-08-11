import type { BusinessResponse } from "../models/business/api/BusinessResponse";
import type { Business } from "../models/business/local/Business";
import { fromUserResponseToUser } from "./user";
import { mainRoute } from "../config/apiRoutes";

export function fromBusinessResponseToBusiness(businessResponse: BusinessResponse): Business{
    return {
        id: businessResponse.id,
        name: businessResponse.name,
        logo: mainRoute+businessResponse.logoUrl,
        owner: fromUserResponseToUser(businessResponse.owner),
        members: businessResponse.users.map(user => fromUserResponseToUser(user))
    }
}