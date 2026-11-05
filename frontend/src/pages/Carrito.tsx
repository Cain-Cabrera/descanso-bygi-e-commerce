import { Link } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { useCarrito } from '../contexts/CarritoContext'

// Formatea el precio en pesos argentinos.
const formatearPrecio = (valor: number) =>
    new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS',
        maximumFractionDigits: 0,
    }).format(valor)

function Carrito() {
    const { carrito, cargando, error, total, cambiarCantidad, quitar, vaciar } =
        useCarrito()

    const items = carrito?.items ?? []

    return (
        <main className="min-h-screen bg-brand-100">

            <Encabezado />

            <section className="mx-auto max-w-4xl px-6 py-12">
                <h2 className="mb-6 text-3xl font-bold text-brand-950">
                    Mi carrito
                </h2>

                {cargando && (
                    <p className="text-brand-800">Cargando carrito...</p>
                )}

                {error && (
                    <p role="alert" className="mb-4 rounded-lg bg-red-100 p-3 text-sm text-red-800">
                        {error}
                    </p>
                )}

                {!cargando && items.length === 0 && (
                    <div className="rounded-xl bg-white p-8 text-center shadow-md">
                        <p className="mb-4 text-brand-700">
                            Tu carrito está vacío.
                        </p>

                        <Link
                            to="/catalogo"
                            className="rounded-lg bg-brand-800 px-6 py-3 font-semibold text-white transition hover:bg-brand-700"
                        >
                            Ir al catálogo
                        </Link>
                    </div>
                )}

                {items.length > 0 && (
                    <>
                        <ul className="space-y-4">
                            {items.map((item) => (
                                <li
                                    key={item.id}
                                    className="flex flex-col gap-4 rounded-xl bg-white p-5 shadow-md sm:flex-row sm:items-center sm:justify-between"
                                >
                                    <div>
                                        <h3 className="text-lg font-bold text-brand-950">
                                            {item.productoNombre}
                                        </h3>
                                        <p className="text-sm text-brand-700">
                                            {formatearPrecio(item.productoUnitario)} c/u
                                        </p>
                                    </div>

                                    <div className="flex items-center gap-3">
                                        <button
                                            type="button"
                                            onClick={() => cambiarCantidad(item.id, item.cantidad - 1)}
                                            disabled={item.cantidad <= 1}
                                            aria-label="Restar una unidad"
                                            className="h-9 w-9 rounded-lg bg-brand-100 font-bold text-brand-950 transition hover:bg-brand-300 disabled:cursor-not-allowed disabled:opacity-50"
                                        >
                                            −
                                        </button>

                                        <span className="w-8 text-center font-semibold text-brand-950">
                                            {item.cantidad}
                                        </span>

                                        <button
                                            type="button"
                                            onClick={() => cambiarCantidad(item.id, item.cantidad + 1)}
                                            aria-label="Sumar una unidad"
                                            className="h-9 w-9 rounded-lg bg-brand-100 font-bold text-brand-950 transition hover:bg-brand-300"
                                        >
                                            +
                                        </button>
                                    </div>

                                    <p className="w-28 text-right text-lg font-bold text-brand-900">
                                        {formatearPrecio(item.subtotal)}
                                    </p>

                                    <button
                                        type="button"
                                        onClick={() => quitar(item.id)}
                                        className="text-sm font-semibold text-red-700 underline"
                                    >
                                        Quitar
                                    </button>
                                </li>
                            ))}
                        </ul>

                        <div className="mt-8 rounded-xl bg-white p-5 shadow-md">
                            <p className="mb-4 text-2xl font-bold text-brand-950">
                                Total: {formatearPrecio(total)}
                            </p>

                            <div className="flex flex-col gap-3 sm:flex-row">
                                <button
                                    type="button"
                                    onClick={vaciar}
                                    className="rounded-lg bg-brand-100 px-4 py-3 font-semibold text-brand-950 transition hover:bg-brand-300"
                                >
                                    Vaciar carrito
                                </button>

                                {/* Deshabilitado hasta conectar el pedido con el backend */}
                                <button
                                    type="button"
                                    disabled
                                    title="Próximamente"
                                    className="rounded-lg bg-brand-800 px-6 py-3 font-semibold text-white transition disabled:cursor-not-allowed disabled:opacity-60 sm:ml-auto"
                                >
                                    Pagar
                                </button>
                            </div>
                        </div>
                    </>
                )}
            </section>

        </main>
    )
}

export default Carrito