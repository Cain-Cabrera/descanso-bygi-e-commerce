import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { obtenerPedido } from '../services/pedidoService'
import type { Pedido } from '../types/pedido'

const precio = (valor: number) => new Intl.NumberFormat('es-AR', { style: 'currency', currency: 'ARS', minimumFractionDigits: 0, maximumFractionDigits: 2 }).format(valor)

function PedidoDetalle() {
    const { id } = useParams()
    const pedidoId = Number(id)
    const idValido = Number.isInteger(pedidoId) && pedidoId > 0
    const [pedido, setPedido] = useState<Pedido | null>(null)
    const [error, setError] = useState('')
    // El backend verifica que el pedido pertenezca a la sesión actual (o que sea una sesión admin).
    useEffect(() => {
        if (!idValido) return
        obtenerPedido(pedidoId).then(setPedido).catch(() => setError('No se encontró el pedido.'))
    }, [id, idValido, pedidoId])

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-4xl px-6 py-10">
                <Link to="/mis-pedidos" className="font-semibold text-brand-800 underline">← Volver a mis pedidos</Link>
                {!idValido && <p className="mt-5 text-red-700" role="alert">Pedido no encontrado.</p>}
                {error && <p className="mt-5 text-red-700" role="alert">{error}</p>}
                {!pedido && !error && <p className="mt-5" role="status">Cargando pedido...</p>}
                {pedido && (
                    <article className="mt-5 rounded-xl bg-white p-6 shadow">
                        <h1 className="text-3xl font-bold text-brand-950">Pedido #{pedido.id}</h1>
                        <p className="mt-2 text-brand-700">Fecha: {new Date(pedido.fechaAlta).toLocaleString('es-AR')}</p>
                        <p className="text-brand-700">Pago: {pedido.formaDePago.toLowerCase()}</p>
                        <ul className="mt-5 divide-y divide-brand-100">
                            {pedido.detalles.map((detalle) => (
                                <li key={detalle.id} className="flex justify-between gap-3 py-3">
                                    <span>{detalle.productoNombre} · {detalle.cantidad} × {precio(detalle.precioUnitario)}</span>
                                    <strong>{precio(detalle.subtotal)}</strong>
                                </li>
                            ))}
                        </ul>
                        <p className="mt-5 text-right text-xl font-bold">Total: {precio(pedido.total)}</p>
                    </article>
                )}
            </section>
        </main>
    )
}

export default PedidoDetalle
