
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'

import Registro from './pages/Registro'
import Login from './pages/Login'

function App() {
    return (
        <BrowserRouter>
            <Routes>

                {/* Página de inicio: por ahora redirige al login */}
                <Route path="/" element={<Navigate to="/login" replace />} />

                {/* Registro de usuarios */}
                <Route path="/registro" element={<Registro />} />

                {/* Inicio de sesión */}
                <Route path="/login" element={<Login />} />

                {/* Cualquier ruta desconocida vuelve al login */}
                <Route path="*" element={<Navigate to="/login" replace />} />

            </Routes>
        </BrowserRouter>
    )
}

export default App
