import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'

import Registro from './pages/Registro'
import Login from './pages/Login'
import Home from './pages/Home'
import Catalogo from './pages/Catalogo'
import Carrito from './pages/Carrito'
import ProductoDetalle from './pages/ProductoDetalle'
import Checkout from './pages/Checkout'
import PedidoConfirmado from './pages/PedidoConfirmado'
import HistorialPedidos from './pages/HistorialPedidos'
import PedidoDetalle from './pages/PedidoDetalle'
import Perfil from './pages/Perfil'
import AdminDashboard from './pages/AdminDashboard'
import AdminProductos from './pages/AdminProductos'
import AdminCatalogo from './pages/AdminCatalogo'
import AdminUsuarios from './pages/AdminUsuarios'
import AdminPedidos from './pages/AdminPedidos'

import RutaProtegida from './components/RutaProtegida'
import RutaPublica from './components/RutaPublica'
import RutaAdministrador from './components/RutaAdministrador'

function App() {
    return (
        <BrowserRouter>
            <Routes>

                {/* Rutas públicas: requieren no tener sesión */}
                <Route element={<RutaPublica />}>
                    <Route path="/login" element={<Login />} />
                    <Route path="/registro" element={<Registro />} />
                </Route>

                {/* El catálogo y sus fichas se pueden explorar sin iniciar sesión. */}
                <Route path="/" element={<Home />} />
                <Route path="/catalogo" element={<Catalogo />} />
                <Route path="/catalogo/:id" element={<ProductoDetalle />} />

                {/* Rutas privadas: requieren tener sesión */}
                <Route element={<RutaProtegida />}>
                    <Route path="/carrito" element={<Carrito />} />
                    <Route path="/checkout" element={<Checkout />} />
                    <Route path="/pedido-confirmado/:id" element={<PedidoConfirmado />} />
                    <Route path="/mis-pedidos" element={<HistorialPedidos />} />
                    <Route path="/mis-pedidos/:id" element={<PedidoDetalle />} />
                    <Route path="/perfil" element={<Perfil />} />
                </Route>

                {/* La interfaz admin exige rol ADMINISTRADOR; el backend aplica el mismo control. */}
                <Route element={<RutaAdministrador />}>
                    <Route path="/admin" element={<AdminDashboard />} />
                    <Route path="/admin/productos" element={<AdminProductos />} />
                    <Route path="/admin/catalogo" element={<AdminCatalogo />} />
                    <Route path="/admin/usuarios" element={<AdminUsuarios />} />
                    <Route path="/admin/pedidos" element={<AdminPedidos />} />
                </Route>

                {/* Rutas desconocidas */}
                <Route path="*" element={<Navigate to="/" replace />} />

            </Routes>
        </BrowserRouter>
    )
}

export default App
