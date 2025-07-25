export interface Response<T>{
    success: {
        code: string
        data: T,
        token?: string
    } | null
    error: {
        code: string,
        message:string
    }| null
}