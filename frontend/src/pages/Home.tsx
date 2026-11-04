import Encabezado from '../components/Encabezado'
import ProductoCard from '../components/ProductoCard'
import { useProductos } from '../hooks/useProductos'

function Home() {
    // Productos del catálogo; en el Home mostramos solo 3 destacados.
    const { productos, cargando, error } = useProductos()
    const destacados = productos.slice(0, 3)

    return (
        <main className="min-h-screen bg-brand-100">

            <Encabezado />

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

            {/* Productos destacados */}
            <section className="mx-auto max-w-7xl px-6 pb-12">
                <h2 className="mb-6 text-2xl font-bold text-brand-950">
                    Productos destacados
                </h2>

                {cargando && (
                    <p className="text-brand-800">Cargando productos...</p>
                )}

                {error && (
                    <p role="alert" className="text-red-700">
                        {error}
                    </p>
                )}

                {!cargando && !error && (
                    destacados.length === 0 ? (
                        <p className="text-brand-700">
                            Todavía no hay productos disponibles.
                        </p>
                    ) : (
                        <ul className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
                            {destacados.map((producto) => (
                                <ProductoCard key={producto.id} producto={producto} />
                            ))}
                        </ul>
                    )
                )}
            </section>

        </main>
    )
}

export default Home