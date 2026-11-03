
import {
    createContext,
    useContext,
    useState,
    type ReactNode,
} from 'react'

import type { LoginResponse } from '../types/auth'

// Representa los datos públicos del usuario autenticado.
interface UsuarioSesion {
    id: number
    nombre: string
    apellido: string
    mail: string
    rol: string
}

// Define las operaciones y datos disponibles en el contexto.
interface AuthContextType {
    usuario: UsuarioSesion | null
    token: string | null
    iniciarSesionContext: (datos: LoginResponse) => void
    cerrarSesion: () => void
}

// Creamos el contexto de autenticación.
const AuthContext = createContext<AuthContextType | undefined>(undefined)

// Proveedor que administra el estado global de autenticación.
export function AuthProvider({ children }: { children: ReactNode }) {

    // Recuperamos la sesión almacenada al iniciar la aplicación.
    const [token, setToken] = useState<string | null>(
        () => localStorage.getItem('token')
    )

    const [usuario, setUsuario] = useState<UsuarioSesion | null>(() => {
        const usuarioGuardado = localStorage.getItem('usuario')

        return usuarioGuardado
            ? JSON.parse(usuarioGuardado)
            : null
    })

    // Guarda los datos recibidos después de iniciar sesión.
    const iniciarSesionContext = (datos: LoginResponse) => {

        const usuarioDatos: UsuarioSesion = {
            id: datos.id,
            nombre: datos.nombre,
            apellido: datos.apellido,
            mail: datos.mail,
            rol: datos.rol,
        }

        localStorage.setItem('token', datos.token)
        localStorage.setItem('usuario', JSON.stringify(usuarioDatos))

        setToken(datos.token)
        setUsuario(usuarioDatos)
    }

    // Elimina la sesión actual.
    const cerrarSesion = () => {
        localStorage.removeItem('token')
        localStorage.removeItem('usuario')

        setToken(null)
        setUsuario(null)
    }

    return (
        <AuthContext.Provider
            value={{
                usuario,
                token,
                iniciarSesionContext,
                cerrarSesion,
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}

// Hook para acceder fácilmente al contexto.
export function useAuth() {
    const contexto = useContext(AuthContext)

    if (!contexto) {
        throw new Error('useAuth debe utilizarse dentro de AuthProvider')
    }

    return contexto
}
