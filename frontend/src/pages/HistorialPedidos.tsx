import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { listarMisPedidos } from '../services/pedidoService'
import type { Pedido } from '../types/pedido'

const precio = (valor: number) => new Intl.NumberFormat('es-AR', { style: 'currency', currency: 'ARS', minimumFractionDigits: 0, maximumFractionDigits: 2 }).format(valor)

function HistorialPedidos() {
    const [pedidos, setPedidos] = useState<Pedido[]>([])
    const [cargando, setCargando] = useState(true)
    const [error, setError] = useState('')
    // El usuario solo recibe sus pedidos: el backend obtiene su identidad desde el token.
    useEffect(() => {
        listarMisPedidos().then(setPedidos).catch(() => setError('No se pudo cargar el historial.')).finally(() => setCargando(false))
    }, [])

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-5xl px-6 py-10">
                <h1 className="text-3xl font-bold text-brand-950">Mis pedidos</h1>
                {cargando && <p className="mt-5" role="status">Cargando pedidos...</p>}
                {error && <p className="mt-5 text-red-700" role="alert">{error}</p>}
                {!cargando && !error && pedidos.length === 0 && <p className="mt-5 text-brand-800">Todavía no realizaste pedidos.</p>}
                <ul className="mt-6 space-y-4">
                    {pedidos.map((pedido) => (
                        <li key={pedido.id} className="rounded-xl bg-white p-5 shadow">
                            <div className="flex flex-wrap items-center justify-between gap-3">
                                <div>
                                    <h2 className="font-bold text-brand-950">Pedido #{pedido.id}</h2>
                                    <p className="text-sm text-brand-700">{new Date(pedido.fechaAlta).toLocaleString('es-AR')} · {pedido.detalles.length} producto(s)</p>
                                </div>
                                <strong>{precio(pedido.total)}</strong>
                            </div>
                            <Link to={`/mis-pedidos/${pedido.id}`} className="mt-3 inline-block font-semibold text-brand-800 underline">Ver detalle</Link>
                        </li>
                    ))}
                </ul>
            </section>
        </main>
    )
}

export default HistorialPedidos
