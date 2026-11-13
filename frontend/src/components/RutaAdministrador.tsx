import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../contexts/useAuth'

function RutaAdministrador() {
    const { token, usuario } = useAuth()
    if (!token) return <Navigate to="/login" replace />
    if (usuario?.rol !== 'ADMINISTRADOR') return <Navigate to="/" replace />
    return <Outlet />
}

export default RutaAdministrador
