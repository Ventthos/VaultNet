import type { User } from "../../users/Local/User";

export interface Business{
    id: number,
    name: string,
    logo: string,
    owner: User
    members: User[]
}