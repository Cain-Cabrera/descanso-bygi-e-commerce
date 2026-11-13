import { createContext } from 'react'
import type { LoginResponse } from '../types/auth'
import type { UsuarioResponse } from '../types/usuario'

// Contrato compartido por el proveedor de sesión y el hook de lectura.
export interface UsuarioSesion {
    id: number
    nombre: string
    apellido: string
    mail: string
    rol: string
}

export interface AuthContextType {
    usuario: UsuarioSesion | null
    token: string | null
    iniciarSesionContext: (datos: LoginResponse) => void
    cerrarSesion: () => void
    actualizarPerfil: (datos: UsuarioResponse) => void
}

export const AuthContext = createContext<AuthContextType | undefined>(undefined)
