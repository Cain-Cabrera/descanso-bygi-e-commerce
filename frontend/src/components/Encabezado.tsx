import { Link, NavLink } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'
import { useCarrito } from '../contexts/CarritoContext'

// Clases del botón de navegación; el activo se resalta.
const claseNavegacion = ({ isActive }: { isActive: boolean }) =>
    `rounded-lg px-4 py-2 text-sm font-semibold transition ${
        isActive
            ? 'bg-brand-700 text-white'
            : 'text-brand-100 hover:bg-brand-800'
    }`

function Encabezado() {
    const { usuario, cerrarSesion } = useAuth()
    const { cantidadTotal } = useCarrito()

    return (
        <header className="bg-brand-950 px-6 py-5 text-brand-100 shadow-md">
            <div className="mx-auto flex max-w-7xl items-center justify-between">

                <Link to="/" className="text-2xl font-bold tracking-wide">
                    Descanso by Gi
                </Link>

                <nav className="flex items-center gap-2">
                    <NavLink to="/" end className={claseNavegacion}>
                        Inicio
                    </NavLink>

                    <NavLink to="/catalogo" className={claseNavegacion}>
                        Catálogo
                    </NavLink>

                    <NavLink to="/carrito" className={claseNavegacion}>
                        Carrito
                        {cantidadTotal > 0 && (
                            <span className="ml-2 rounded-full bg-brand-300 px-2 py-0.5 text-xs font-bold text-brand-950">
                                {cantidadTotal}
                            </span>
                        )}
                    </NavLink>
                </nav>

                <div className="flex items-center gap-5">
                    <span className="hidden text-sm md:block">
                        Hola, {usuario?.nombre}
                    </span>

                    <button
                        onClick={cerrarSesion}
                        className="rounded-lg bg-brand-700 px-4 py-2 text-sm font-semibold transition hover:bg-brand-800"
                    >
                        Cerrar sesión
                    </button>
                </div>

            </div>
        </header>
    )
}

export default Encabezado