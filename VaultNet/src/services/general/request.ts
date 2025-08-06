import axios, { AxiosError } from 'axios';
import type { ApiResponse } from '../../models/api/ApiResponse';
import type { Response } from '../../models/api/Response';
import { mainRoute } from '../../config/apiRoutes';

const api = axios.create({
  baseURL: mainRoute,
  headers: {
    Accept: 'application/json'
  },
  withCredentials: true
});

api.interceptors.response.use(response=>response,
    error=>{
        const axiosError = error as AxiosError;
        if(!axiosError.response){
            // Cambiar por traductor
            return Promise.reject(new Error("Sin conexión al servidor"));
        }

        const data = axiosError.response.data as Partial<ApiResponse<any>>;;
        const message = data?.message || 'Error inesperado';
        const status = axiosError.response.status;
        return Promise.reject({ message, status, raw: data });
    }
)

export async function request<T>(config: {
  method: 'GET' | 'POST' | 'PATCH' | 'PUT' | 'DELETE';
  url: string;
  params?: Record<string, any>;
  data?: any;
  token?: string;
}
): Promise<Response<T>>{
    try{
        const isFormData = config.data instanceof FormData;
        const response = await api.request<T>({
            method: config.method,
            url: config.url,
            params: config.params,
            data: config.data,
            headers: {
                ...(config.token ? { Authorization: `Bearer ${config.token}` } : {}),
                ...(isFormData ? {} : { 'Content-Type': 'application/json' })
            }
        });

         const result = response.data;

        return {
            success: {
                code: (result as any).code,
                data: (result as any).data,
                token: (result as any).token
            },
            error: null,
        };

    }
    catch(err: any){
        if (err?.raw?.code) {
            return {
                success: null,
                error: {
                    code: err.raw.code,
                    message: err.raw.message ?? 'Error desconocido'
                }
            };
        }

        // Si no hay estructura esperada
        return {
            success: null,
            error: {
                code: "UNHANDLED_ERROR",
                message: err?.message ?? 'Error inesperado'
            }
        };
    }
}