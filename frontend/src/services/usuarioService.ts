
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
