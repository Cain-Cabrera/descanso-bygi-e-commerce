import axios from 'axios'
import type { UsuarioRequest, UsuarioResponse } from '../types/usuario'

// Dirección base del backend.
const API_URL = import.meta.env.VITE_API_URL

// Instancia centralizada de Axios.
const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
})

// Registra un usuario y devuelve los datos recibidos del backend.
export const registrarUsuario = async (
    usuario: UsuarioRequest
): Promise<UsuarioResponse> => {
    const respuesta = await api.post<UsuarioResponse>(
        '/api/usuarios/registrar',
        usuario
    )

    return respuesta.data
}