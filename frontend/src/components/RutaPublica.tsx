
import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'

function RutaPublica() {
    // Consultamos si existe una sesión activa.
    const { token } = useAuth()

    // Si ya inició sesión, no necesita volver al login o registro.
    if (token) {
        return <Navigate to="/" replace />
    }

    // Si no hay sesión, permitimos acceder a las rutas públicas.
    return <Outlet />
}

export default RutaPublica
