import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { adminService } from '../services/adminService'
import type { Pedido } from '../types/pedido'

const precio = (valor: number) => new Intl.NumberFormat('es-AR', { style: 'currency', currency: 'ARS', minimumFractionDigits: 0, maximumFractionDigits: 2 }).format(valor)

// La API muestra pedidos activos y devuelve el detalle; cancelar repone el stock en el backend.
function AdminPedidos() {
    const [pedidos, setPedidos] = useState<Pedido[]>([])
    const [error, setError] = useState('')
    const cargar = async () => {
        try { setPedidos(await adminService.pedidos()) }
        catch { setError('No se pudieron cargar los pedidos. Verificá que tu cuenta tenga rol ADMINISTRADOR.') }
    }
    useEffect(() => {
        let vigente = true
        adminService.pedidos().then((lista) => { if (vigente) setPedidos(lista) })
            .catch(() => { if (vigente) setError('No se pudieron cargar los pedidos. Verificá que tu cuenta tenga rol ADMINISTRADOR.') })
        return () => { vigente = false }
    }, [])

    const cancelar = async (pedido: Pedido) => {
        if (!window.confirm(`¿Cancelar el pedido #${pedido.id}? La operación reintegrará el stock de sus productos.`)) return
        try { await adminService.cancelarPedido(pedido.id); await cargar() }
        catch { setError('No se pudo cancelar el pedido.') }
    }

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-6xl px-6 py-10">
                <Link to="/admin" className="font-semibold text-brand-800 underline">← Panel</Link>
                <h1 className="mt-4 text-3xl font-bold text-brand-950">Gestión de pedidos</h1>
                {error && <p role="alert" className="mt-4 text-red-700">{error}</p>}
                <ul className="mt-6 space-y-4">{pedidos.map((pedido) => (
                    <li key={pedido.id} className="rounded-xl bg-white p-5 shadow">
                        <div className="flex flex-wrap justify-between gap-3"><div><h2 className="font-bold">Pedido #{pedido.id} {pedido.cancelado && <span className="text-sm font-medium text-red-700">(cancelado)</span>}</h2><p className="text-sm text-brand-700">Usuario #{pedido.usuarioId} · {new Date(pedido.fechaAlta).toLocaleString('es-AR')} · {pedido.formaDePago}</p></div><strong>{precio(pedido.total)}</strong></div>
                        <ul className="mt-3 list-inside list-disc text-sm text-brand-800">{pedido.detalles.map((detalle) => <li key={detalle.id}>{detalle.productoNombre} × {detalle.cantidad}</li>)}</ul>
                        {!pedido.cancelado && <button onClick={() => void cancelar(pedido)} className="mt-4 rounded bg-red-100 px-4 py-2 font-semibold text-red-800">Cancelar pedido</button>}
                    </li>
                ))}</ul>
            </section>
        </main>
    )
}

export default AdminPedidos
