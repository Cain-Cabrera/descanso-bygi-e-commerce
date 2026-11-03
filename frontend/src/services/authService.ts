
import axios from 'axios'
import type { LoginRequest, LoginResponse } from '../types/auth'

const API_URL = import.meta.env.VITE_API_URL

// Configuración de Axios para comunicarnos con Spring Boot.
const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
})

// Envía las credenciales y devuelve los datos del usuario autenticado.
export const iniciarSesion = async (
    datos: LoginRequest
): Promise<LoginResponse> => {

    const respuesta = await api.post<LoginResponse>(
        '/api/auth/login',
        datos
    )

    return respuesta.data
}
