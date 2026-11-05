import { useState } from 'react'
import { useCarrito } from '../contexts/CarritoContext'
import type { Producto } from '../types/producto'

interface ProductoCardProps {
    producto: Producto
}

// Formatea el precio en pesos argentinos.
const formatearPrecio = (valor: number) =>
    new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS',
        maximumFractionDigits: 0,
    }).format(valor)

function ProductoCard({ producto }: ProductoCardProps) {
    const { agregar } = useCarrito()

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

            {/* Espacio reservado para la imagen del producto */}
            <div className="mb-4 flex h-40 items-center justify-center rounded-lg bg-brand-100 text-sm text-brand-700">
                Sin imagen
            </div>

            <div className="mb-2 flex flex-wrap gap-2">
                <span className="rounded-full bg-brand-100 px-3 py-1 text-xs font-medium text-brand-800">
                    {producto.categoriaNombre}
                </span>
                <span className="rounded-full bg-brand-100 px-3 py-1 text-xs font-medium text-brand-800">
                    {producto.marcaNombre}
                </span>
            </div>

            <h3 className="text-lg font-bold text-brand-950">
                {producto.nombre}
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

            <button
                type="button"
                disabled={sinStock || agregando}
                onClick={manejarAgregar}
                className="mt-4 w-full rounded-lg bg-brand-800 px-4 py-2 font-semibold text-white transition hover:bg-brand-700 disabled:cursor-not-allowed disabled:opacity-60"
            >
                {agregando ? 'Agregando...' : 'Agregar al carrito'}
            </button>

            {mensaje && (
                <p role="status" className="mt-2 text-center text-sm text-brand-800">
                    {mensaje}
                </p>
            )}
        </li>
    )
}

export default ProductoCard