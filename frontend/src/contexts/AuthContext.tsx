
import {
    useEffect,
    useState,
    type ReactNode,
} from 'react'
import { AuthContext, type UsuarioSesion } from './authState'
import type { LoginResponse } from '../types/auth'
import type { UsuarioResponse } from '../types/usuario'

// Proveedor que administra el estado global de autenticación.
export function AuthProvider({ children }: { children: ReactNode }) {

    // Recupera solo tokens con una sesión completa y dentro del tiempo informado por el backend.
    const [token, setToken] = useState<string | null>(() => {
        const tokenGuardado = localStorage.getItem('token')
        const expira = Number(localStorage.getItem('tokenExpira'))
        if (!tokenGuardado || !Number.isFinite(expira) || expira <= Date.now()) {
            localStorage.removeItem('token')
            localStorage.removeItem('tokenExpira')
            return null
        }
        return tokenGuardado
    })

    const [usuario, setUsuario] = useState<UsuarioSesion | null>(() => {
        const usuarioGuardado = localStorage.getItem('usuario')
        try {
            const usuarioParseado: unknown = usuarioGuardado ? JSON.parse(usuarioGuardado) : null
            if (typeof usuarioParseado === 'object' && usuarioParseado !== null
                && 'id' in usuarioParseado && 'nombre' in usuarioParseado && 'rol' in usuarioParseado
                && typeof usuarioParseado.id === 'number'
                && typeof usuarioParseado.nombre === 'string'
                && typeof usuarioParseado.rol === 'string'
                && localStorage.getItem('token')) {
                return usuarioParseado as UsuarioSesion
            }
        } catch {
            // Una sesión guardada corrupta no debe impedir que la aplicación arranque.
        }
        localStorage.removeItem('token')
        localStorage.removeItem('tokenExpira')
        localStorage.removeItem('usuario')
        return null
    })

    // Si una petición recibe 401, cerramos la sesión para evitar operar con un token vencido.
    useEffect(() => {
        const cerrarSesionVencida = () => {
            localStorage.removeItem('token')
            localStorage.removeItem('tokenExpira')
            localStorage.removeItem('usuario')
            setToken(null)
            setUsuario(null)
        }
        window.addEventListener('sesion-expirada', cerrarSesionVencida)
        return () => window.removeEventListener('sesion-expirada', cerrarSesionVencida)
    }, [])

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
        localStorage.setItem('tokenExpira', String(Date.now() + datos.expiraEn * 1000))
        localStorage.setItem('usuario', JSON.stringify(usuarioDatos))

        setToken(datos.token)
        setUsuario(usuarioDatos)
    }

    // Elimina la sesión actual.
    const cerrarSesion = () => {
        localStorage.removeItem('token')
        localStorage.removeItem('tokenExpira')
        localStorage.removeItem('usuario')

        setToken(null)
        setUsuario(null)
    }

    const actualizarPerfil = (datos: UsuarioResponse) => {
        setUsuario((actual) => {
            if (!actual) return actual
            const actualizado = { ...actual, ...datos }
            localStorage.setItem('usuario', JSON.stringify(actualizado))
            return actualizado
        })
    }

    return (
        <AuthContext.Provider
            value={{
                usuario,
                token,
                iniciarSesionContext,
                cerrarSesion,
                actualizarPerfil,
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}
