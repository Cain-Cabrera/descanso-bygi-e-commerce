import { useEffect, useState } from 'react'
import { listarProductos } from '../services/productoService'
import type { Producto } from '../types/producto'
import { useAuth } from '../contexts/AuthContext'

function Home() {
    // Recuperamos los datos y acciones de autenticación.
    const { usuario, cerrarSesion } = useAuth()

    // Estado de productos.
    const [productos, setProductos] = useState<Producto[]>([])
    const [cargandoProductos, setCargandoProductos] = useState(true)
    const [errorProductos, setErrorProductos] = useState('')

// Consulta el catálogo cuando se carga el Home.
    useEffect(() => {
        const cargarProductos = async () => {
            try {
                const datos = await listarProductos()
                setProductos(datos)
            } catch (error) {
                console.error('Error al cargar productos:', error)
                setErrorProductos('No se pudieron cargar los productos.')
            } finally {
                setCargandoProductos(false)
            }

        }

            cargarProductos()
    }, [])

    return (
        <main className="min-h-screen bg-brand-100">

            {/* Encabezado */}
            <header className="bg-brand-950 px-6 py-5 text-brand-100 shadow-md">
                <div className="mx-auto flex max-w-7xl items-center justify-between">

                    <h1 className="text-2xl font-bold tracking-wide">
                        Descanso by Gi
                    </h1>

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

            {/* Presentación */}
            <section className="mx-auto max-w-7xl px-6 py-12">
                <h2 className="text-3xl font-bold text-brand-950">
                    Encontrá tu descanso ideal
                </h2>

                <p className="mt-3 max-w-2xl text-brand-800">
                    Descubrí nuestros productos y encontrá lo que necesitás
                    para disfrutar de un mejor descanso.
                </p>
            </section>

            {/* Catálogo de productos */}
            <section className="mx-auto max-w-7xl px-6 pb-12">
                <h2 className="mb-6 text-2xl font-bold text-brand-950">
                    Nuestro catálogo
                </h2>

                {cargandoProductos && (
                    <p className="text-brand-800">Cargando productos...</p>
                )}

                {errorProductos && (
                    <p role="alert" className="text-red-700">
                        {errorProductos}
                    </p>
                )}

                {!cargandoProductos && !errorProductos && (
                    <>
                        {productos.length === 0 ? (
                            <p className="text-brand-700">
                                Todavía no hay productos disponibles.
                            </p>
                        ) : (
                            <ul className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
                                {productos.map((producto) => (
                                    <li
                                        key={producto.id}
                                        className="rounded-xl bg-white p-5 shadow-md transition hover:shadow-xl"
                                    >
                                        <h3 className="text-lg font-bold text-brand-950">
                                            {producto.nombre}
                                        </h3>

                                        <p className="mt-3 text-brand-800">
                                            Precio: ${producto.precio}
                                        </p>

                                        <p className="mt-1 text-sm text-brand-700">
                                            Stock disponible: {producto.stock}
                                        </p>
                                    </li>
                                ))}
                            </ul>
                        )}
                    </>
                )}
            </section>

        </main>
    )
}

export default Home