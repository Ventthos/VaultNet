import type { ApiResponse } from "./ApiResponse";

export interface ApiResponseWithToken<T> extends ApiResponse<T>{
    token: string
}