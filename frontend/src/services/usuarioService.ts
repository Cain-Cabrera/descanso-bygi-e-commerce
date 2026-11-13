
import api from './api'
import type { UsuarioRequest, UsuarioResponse } from '../types/usuario'

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

export type UsuarioPerfilEdit = Partial<Pick<UsuarioRequest, 'nombre' | 'apellido' | 'mail' | 'celular'>>

export const obtenerMiPerfil = async (): Promise<UsuarioResponse> => {
    const respuesta = await api.get<UsuarioResponse>('/api/usuarios/me')
    return respuesta.data
}

export const actualizarMiPerfil = async (datos: UsuarioPerfilEdit): Promise<UsuarioResponse> => {
    const respuesta = await api.patch<UsuarioResponse>('/api/usuarios/me', datos)
    return respuesta.data
}
