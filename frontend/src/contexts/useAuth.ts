import { useContext } from 'react'
import { AuthContext } from './authState'

// Hook con validación para evitar usar la sesión fuera de AuthProvider.
export function useAuth() {
    const contexto = useContext(AuthContext)
    if (!contexto) throw new Error('useAuth debe utilizarse dentro de AuthProvider')
    return contexto
}
