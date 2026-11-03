
import { Navigate, Outlet } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'

function RutaProtegida() {
    // Recuperamos el token desde el contexto global.
    const { token } = useAuth()

    // Si no existe una sesión, redirigimos al login.
    if (!token) {
        return <Navigate to="/login" replace />
    }

    // Si existe una sesión, permitimos acceder a la ruta.
    return <Outlet />
}

export default RutaProtegida
