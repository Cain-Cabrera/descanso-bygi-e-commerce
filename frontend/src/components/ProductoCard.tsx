import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useCarrito } from '../contexts/useCarrito'
import { useAuth } from '../contexts/useAuth'
import type { Producto } from '../types/producto'

interface ProductoCardProps {
    producto: Producto
}

// Formatea el precio en pesos argentinos.
const formatearPrecio = (valor: number) =>
    new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS',
        minimumFractionDigits: 0,
        maximumFractionDigits: 2,
    }).format(valor)

function ProductoCard({ producto }: ProductoCardProps) {
    const { agregar, cargando: carritoCargando } = useCarrito()
    const { usuario } = useAuth()

    const [agregando, setAgregando] = useState(false)
    const [mensaje, setMensaje] = useState('')

    const sinStock = producto.stock === 0

    // Agrega una unidad al carrito y avisa el resultado.
    const manejarAgregar = async () => {
        setAgregando(true)
        setMensaje('')

        const exito = await agregar({ productoId: producto.id, cantidad: 1 })

        setMensaje(exito ? '¡Agregado al carrito!' : 'No se pudo agregar.')
        setAgregando(false)

        // El mensaje desaparece solo a los 2 segundos.
        setTimeout(() => setMensaje(''), 2000)
    }

    return (
        <li className="flex flex-col rounded-xl bg-white p-5 shadow-md transition hover:shadow-xl">

            <Link to={`/catalogo/${producto.id}`} aria-label={`Ver ${producto.nombre}`}>
                {producto.imagenes?.[0] ? (
                    <img
                        src={producto.imagenes[0]}
                        alt={producto.nombre}
                        className="mb-4 h-40 w-full rounded-lg bg-brand-100 object-cover"
                        loading="lazy"
                    />
                ) : (
                    <div className="mb-4 flex h-40 items-center justify-center rounded-lg bg-brand-100 text-sm text-brand-700">
                        Sin imagen
                    </div>
                )}
            </Link>

            <div className="mb-2 flex flex-wrap gap-2">
                <span className="rounded-full bg-brand-100 px-3 py-1 text-xs font-medium text-brand-800">
                    {producto.categoriaNombre}
                </span>
                <span className="rounded-full bg-brand-100 px-3 py-1 text-xs font-medium text-brand-800">
                    {producto.marcaNombre}
                </span>
            </div>

            <h3 className="text-lg font-bold text-brand-950">
                <Link to={`/catalogo/${producto.id}`} className="hover:underline">
                {producto.nombre}
                </Link>
            </h3>

            <p className="mt-1 line-clamp-2 text-sm text-brand-700">
                {producto.descripcion}
            </p>

            <p className="mt-3 text-xl font-bold text-brand-900">
                {formatearPrecio(producto.precio)}
            </p>

            <p className={`mt-1 text-sm ${sinStock ? 'text-red-700' : 'text-brand-700'}`}>
                {sinStock ? 'Sin stock' : `Stock disponible: ${producto.stock}`}
            </p>

            {usuario ? <button
                type="button"
                disabled={sinStock || agregando || carritoCargando}
                onClick={manejarAgregar}
                className="mt-4 w-full rounded-lg bg-brand-800 px-4 py-2 font-semibold text-white transition hover:bg-brand-700 disabled:cursor-not-allowed disabled:opacity-60"
            >
                {agregando ? 'Agregando...' : 'Agregar al carrito'}
            </button> : (
                <Link
                    to="/login"
                    className="mt-4 w-full rounded-lg bg-brand-800 px-4 py-2 text-center font-semibold text-white transition hover:bg-brand-700"
                >
                    Iniciá sesión para comprar
                </Link>
            )}

            {mensaje && (
                <p role="status" className="mt-2 text-center text-sm text-brand-800">
                    {mensaje}
                </p>
            )}
        </li>
    )
}

export default ProductoCard
