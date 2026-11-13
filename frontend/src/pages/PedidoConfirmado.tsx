import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { obtenerPedido } from '../services/pedidoService'
import type { Pedido } from '../types/pedido'

const formatearPrecio = (valor: number) =>
    new Intl.NumberFormat('es-AR', { style: 'currency', currency: 'ARS', minimumFractionDigits: 0, maximumFractionDigits: 2 }).format(valor)

function PedidoConfirmado() {
    const { id } = useParams()
    const pedidoId = Number(id)
    const idValido = Number.isInteger(pedidoId) && pedidoId > 0
    const [pedido, setPedido] = useState<Pedido | null>(null)
    const [error, setError] = useState('')

    useEffect(() => {
        let vigente = true
        if (!idValido) return () => { vigente = false }
        obtenerPedido(pedidoId).then((resultado) => {
            if (vigente) setPedido(resultado)
        }).catch(() => {
            if (vigente) setError('No se pudo cargar la confirmación del pedido.')
        })
        return () => { vigente = false }
    }, [id, idValido, pedidoId])

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-2xl px-6 py-12">
                <div className="rounded-xl bg-white p-8 shadow">
                    <h1 className="text-3xl font-bold text-brand-950">¡Pedido recibido!</h1>
                    {pedido ? (
                        <>
                            <p className="mt-3 text-brand-800">Número de pedido: <strong>#{pedido.id}</strong></p>
                            <p className="mt-1 text-brand-800">Total: <strong>{formatearPrecio(pedido.total)}</strong></p>
                            <p className="mt-1 text-brand-800">Forma de pago: {pedido.formaDePago.toLowerCase()}</p>
                            <p className="mt-5 text-brand-700">El pago no se realiza desde esta página. Para coordinar {pedido.formaDePago.toLowerCase()} y la entrega, comunicate con Descanso by Gi por WhatsApp a través de sus canales habituales.{pedido.formaDePago === 'TRANSFERENCIA' ? ' Solicitá allí los datos para realizar la transferencia.' : ''}</p>
                            <Link to={`/mis-pedidos/${pedido.id}`} className="mt-6 inline-block rounded-lg bg-brand-800 px-5 py-3 font-semibold text-white">Ver detalle del pedido</Link>
                        </>
                    ) : !idValido ? <p role="alert" className="mt-4 text-red-700">No se encontró el pedido.</p> : error ? <p role="alert" className="mt-4 text-red-700">{error}</p> : <p role="status" className="mt-4">Cargando confirmación...</p>}
                    <Link to="/catalogo" className="mt-4 block font-semibold text-brand-800 underline">Volver al catálogo</Link>
                </div>
            </section>
        </main>
    )
}

export default PedidoConfirmado
