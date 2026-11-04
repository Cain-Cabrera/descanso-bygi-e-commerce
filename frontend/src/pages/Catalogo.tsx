import { useMemo, useState } from 'react'
import Encabezado from '../components/Encabezado'
import ProductoCard from '../components/ProductoCard'
import { useProductos } from '../hooks/useProductos'

function Catalogo() {
    const { productos, cargando, error } = useProductos()

    // Filtros elegidos por el usuario.
    const [busqueda, setBusqueda] = useState('')
    const [categoria, setCategoria] = useState('')
    const [marca, setMarca] = useState('')

    // Opciones de los selects, sin repetidos y ordenadas.
    const categorias = useMemo(
        () => [...new Set(productos.map((p) => p.categoriaNombre))].sort(),
        [productos]
    )
    const marcas = useMemo(
        () => [...new Set(productos.map((p) => p.marcaNombre))].sort(),
        [productos]
    )

    // Aplica los tres filtros a la vez.
    const productosFiltrados = useMemo(() => {
        const texto = busqueda.trim().toLowerCase()

        return productos.filter(
            (p) =>
                p.nombre.toLowerCase().includes(texto) &&
                (categoria === '' || p.categoriaNombre === categoria) &&
                (marca === '' || p.marcaNombre === marca)
        )
    }, [productos, busqueda, categoria, marca])

    const limpiarFiltros = () => {
        setBusqueda('')
        setCategoria('')
        setMarca('')
    }

    const hayFiltros = busqueda !== '' || categoria !== '' || marca !== ''

    return (
        <main className="min-h-screen bg-brand-100">

            <Encabezado />

            <section className="mx-auto max-w-7xl px-6 py-12">
                <h2 className="mb-6 text-3xl font-bold text-brand-950">
                    Catálogo completo
                </h2>

                {/* Filtros */}
                <div className="mb-8 grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-4">

                    <input
                        type="search"
                        value={busqueda}
                        onChange={(e) => setBusqueda(e.target.value)}
                        placeholder="Buscar por nombre"
                        aria-label="Buscar por nombre"
                        className="rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                    />

                    <select
                        value={categoria}
                        onChange={(e) => setCategoria(e.target.value)}
                        aria-label="Filtrar por categoría"
                        className="rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                    >
                        <option value="">Todas las categorías</option>
                        {categorias.map((c) => (
                            <option key={c} value={c}>{c}</option>
                        ))}
                    </select>

                    <select
                        value={marca}
                        onChange={(e) => setMarca(e.target.value)}
                        aria-label="Filtrar por marca"
                        className="rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                    >
                        <option value="">Todas las marcas</option>
                        {marcas.map((m) => (
                            <option key={m} value={m}>{m}</option>
                        ))}
                    </select>

                    <button
                        type="button"
                        onClick={limpiarFiltros}
                        disabled={!hayFiltros}
                        className="rounded-lg bg-brand-800 px-4 py-3 font-semibold text-white transition hover:bg-brand-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        Limpiar filtros
                    </button>

                </div>

                {cargando && (
                    <p className="text-brand-800">Cargando productos...</p>
                )}

                {error && (
                    <p role="alert" className="text-red-700">
                        {error}
                    </p>
                )}

                {!cargando && !error && (
                    productosFiltrados.length === 0 ? (
                        <p className="text-brand-700">
                            No encontramos productos con esos filtros.
                        </p>
                    ) : (
                        <>
                            <p className="mb-4 text-sm text-brand-700">
                                {productosFiltrados.length} producto(s)
                            </p>

                            <ul className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-3">
                                {productosFiltrados.map((producto) => (
                                    <ProductoCard key={producto.id} producto={producto} />
                                ))}
                            </ul>
                        </>
                    )
                )}
            </section>

        </main>
    )
}

export default Catalogo