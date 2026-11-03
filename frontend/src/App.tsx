
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'

import Registro from './pages/Registro'
import Login from './pages/Login'
import Home from './pages/Home'

import RutaProtegida from './components/RutaProtegida'
import RutaPublica from './components/RutaPublica'

function App() {
    return (
        <BrowserRouter>
            <Routes>

                {/* Rutas públicas: requieren no tener sesión */}
                <Route element={<RutaPublica />}>
                    <Route path="/login" element={<Login />} />
                    <Route path="/registro" element={<Registro />} />
                </Route>

                {/* Rutas privadas: requieren tener sesión */}
                <Route element={<RutaProtegida />}>
                    <Route path="/" element={<Home />} />
                </Route>

                {/* Rutas desconocidas */}
                <Route path="*" element={<Navigate to="/" replace />} />

            </Routes>
        </BrowserRouter>
    )
}

export default App
