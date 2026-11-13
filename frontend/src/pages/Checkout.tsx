import { useState, type FormEvent } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { useCarrito } from '../contexts/useCarrito'
import { crearPedido } from '../services/pedidoService'
import type { FormaDePago } from '../types/pedido'

const formatearPrecio = (valor: number) =>
    new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS',
        minimumFractionDigits: 0,
        maximumFractionDigits: 2,
    }).format(valor)

function Checkout() {
    const { carrito, cargando, total, recargar } = useCarrito()
    const navigate = useNavigate()
    const [formaDePago, setFormaDePago] = useState<FormaDePago>('TRANSFERENCIA')
    const [enviando, setEnviando] = useState(false)
    const [error, setError] = useState('')

    // La API usa el carrito autenticado y recalcula el total y el stock; no confiamos en el total del navegador.
    const confirmar = async (event: FormEvent) => {
        event.preventDefault()
        setEnviando(true)
        setError('')
        try {
            const pedido = await crearPedido(formaDePago)
            // La confirmación ya se guardó. Si falla solo la actualización del carrito, igual mostramos el pedido.
            try { await recargar() } catch { /* El pedido ya quedó creado. */ }
            navigate(`/pedido-confirmado/${pedido.id}`)
        } catch {
            setError('No se pudo confirmar el pedido. Revisá el stock y volvé a intentar.')
        } finally {
            setEnviando(false)
        }
    }

    const items = carrito?.items ?? []

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-3xl px-6 py-10">
                <h1 className="text-3xl font-bold text-brand-950">Confirmar pedido</h1>
                {cargando && <p className="mt-4" role="status">Cargando carrito...</p>}
                {!cargando && items.length === 0 && (
                    <div className="mt-6 rounded-xl bg-white p-6 shadow">
                        <p>Tu carrito está vacío.</p>
                        <Link to="/catalogo" className="mt-3 inline-block font-semibold text-brand-800 underline">Volver al catálogo</Link>
                    </div>
                )}
                {!cargando && items.length > 0 && (
                    <form onSubmit={(event) => void confirmar(event)} className="mt-6 space-y-6 rounded-xl bg-white p-6 shadow">
                        <ul className="divide-y divide-brand-100">
                            {items.map((item) => (
                                <li key={item.id} className="flex justify-between gap-4 py-3">
                                    <span>{item.productoNombre} × {item.cantidad}</span>
                                    <span className="font-semibold">{formatearPrecio(item.subtotal)}</span>
                                </li>
                            ))}
                        </ul>
                        <p className="text-right text-xl font-bold text-brand-950">Total: {formatearPrecio(total)}</p>

                        <label className="block font-semibold text-brand-950">
                            Forma de pago
                            <select
                                value={formaDePago}
                                onChange={(event) => setFormaDePago(event.target.value as FormaDePago)}
                                className="mt-2 block w-full rounded-lg border border-brand-300 bg-white px-4 py-3"
                            >
                                <option value="TRANSFERENCIA">Transferencia</option>
                                <option value="EFECTIVO">Efectivo</option>
                                <option value="TARJETA">Tarjeta</option>
                            </select>
                        </label>

                        <p className="text-sm text-brand-700">Al confirmar, el sistema vuelve a verificar el stock y registra tu pedido. El pago no se realiza en esta página: contactá a Descanso by Gi por WhatsApp para coordinar el pago elegido y la entrega. Si seleccionaste transferencia, solicitá los datos para realizarla.</p>
                        {error && <p role="alert" className="rounded-lg bg-red-100 p-3 text-red-800">{error}</p>}
                        <div className="flex flex-col justify-between gap-3 sm:flex-row">
                            <Link to="/carrito" className="rounded-lg bg-brand-100 px-5 py-3 text-center font-semibold text-brand-950">Volver al carrito</Link>
                            <button type="submit" disabled={enviando} className="rounded-lg bg-brand-800 px-6 py-3 font-semibold text-white hover:bg-brand-700 disabled:opacity-60">
                                {enviando ? 'Confirmando...' : 'Confirmar pedido'}
                            </button>
                        </div>
                    </form>
                )}
            </section>
        </main>
    )
}

export default Checkout
