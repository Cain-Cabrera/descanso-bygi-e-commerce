
import api from './api'
import type { LoginRequest, LoginResponse } from '../types/auth'

// Envía las credenciales al backend y devuelve la respuesta.
export const iniciarSesion = async (
    datos: LoginRequest
): Promise<LoginResponse> => {

    const respuesta = await api.post<LoginResponse>(
        '/api/auth/login',
        datos
    )

    return respuesta.data
}
