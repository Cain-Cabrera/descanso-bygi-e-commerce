import { useEffect, useState, type FormEvent } from 'react'
import { Link } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { adminService, type CategoriaAdmin, type MarcaAdmin, type ProductoAdminInput } from '../services/adminService'
import type { Producto } from '../types/producto'

const formularioVacio: ProductoAdminInput = { nombre: '', descripcion: '', precio: 0, stock: 0, marcaId: 0, categoriaId: 0, imagenes: [] }

// Mantiene el formulario y el listado sincronizados con los cambios confirmados por el backend.
function AdminProductos() {
    const [productos, setProductos] = useState<Producto[]>([])
    const [categorias, setCategorias] = useState<CategoriaAdmin[]>([])
    const [marcas, setMarcas] = useState<MarcaAdmin[]>([])
    const [formulario, setFormulario] = useState<ProductoAdminInput>(formularioVacio)
    const [editando, setEditando] = useState<number | null>(null)
    const [error, setError] = useState('')
    const [guardando, setGuardando] = useState(false)

    const cargar = async () => {
        try {
            const [listaProductos, listaCategorias, listaMarcas] = await Promise.all([
                adminService.productos(), adminService.categorias(), adminService.marcas(),
            ])
            setProductos(listaProductos); setCategorias(listaCategorias); setMarcas(listaMarcas)
        } catch {
            setError('No se pudieron cargar los datos. Verificá que tu cuenta tenga rol ADMINISTRADOR.')
        }
    }
    useEffect(() => {
        let vigente = true
        Promise.all([adminService.productos(), adminService.categorias(), adminService.marcas()])
            .then(([listaProductos, listaCategorias, listaMarcas]) => {
                if (vigente) { setProductos(listaProductos); setCategorias(listaCategorias); setMarcas(listaMarcas) }
            })
            .catch(() => { if (vigente) setError('No se pudieron cargar los datos. Verificá que tu cuenta tenga rol ADMINISTRADOR.') })
        return () => { vigente = false }
    }, [])

    const guardar = async (event: FormEvent) => {
        event.preventDefault(); setGuardando(true); setError('')
        try {
            if (editando) await adminService.editarProducto(editando, formulario)
            else await adminService.crearProducto(formulario)
            setFormulario(formularioVacio); setEditando(null); await cargar()
        } catch {
            setError('No se pudo guardar. Revisá que nombre, precio, stock, marca y categoría sean válidos.')
        } finally { setGuardando(false) }
    }

    const prepararEdicion = (producto: Producto) => {
        setEditando(producto.id)
        setFormulario({
            nombre: producto.nombre, descripcion: producto.descripcion ?? '', precio: producto.precio,
            stock: producto.stock, marcaId: producto.marcaId, categoriaId: producto.categoriaId,
            imagenes: producto.imagenes ?? [],
        })
        window.scrollTo({ top: 0, behavior: 'smooth' })
    }

    const eliminar = async (producto: Producto) => {
        if (!window.confirm(`¿Dar de baja el producto “${producto.nombre}”?`)) return
        try { await adminService.eliminarProducto(producto.id); await cargar() }
        catch { setError('No se pudo dar de baja el producto.') }
    }

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-7xl px-6 py-10">
                <Link to="/admin" className="font-semibold text-brand-800 underline">← Panel</Link>
                <h1 className="mt-4 text-3xl font-bold text-brand-950">Gestión de productos</h1>

                <form onSubmit={(event) => void guardar(event)} className="mt-6 grid gap-4 rounded-xl bg-white p-6 shadow sm:grid-cols-2">
                    <h2 className="text-xl font-bold sm:col-span-2">{editando ? 'Editar producto' : 'Nuevo producto'}</h2>
                    <label className="font-semibold">Nombre<input required maxLength={150} value={formulario.nombre} onChange={(e) => setFormulario({ ...formulario, nombre: e.target.value })} className="mt-1 block w-full rounded border p-2 font-normal" /></label>
                    <label className="font-semibold">Precio<input required min="0" step="0.01" type="number" value={formulario.precio} onChange={(e) => setFormulario({ ...formulario, precio: Number(e.target.value) })} className="mt-1 block w-full rounded border p-2 font-normal" /></label>
                    <label className="font-semibold">Stock<input required min="0" step="1" type="number" value={formulario.stock} onChange={(e) => setFormulario({ ...formulario, stock: Number(e.target.value) })} className="mt-1 block w-full rounded border p-2 font-normal" /></label>
                    <label className="font-semibold">Marca<select required value={formulario.marcaId || ''} onChange={(e) => setFormulario({ ...formulario, marcaId: Number(e.target.value) })} className="mt-1 block w-full rounded border p-2 font-normal"><option value="" disabled>Elegí una marca</option>{marcas.map((m) => <option key={m.id} value={m.id}>{m.nombre}</option>)}</select></label>
                    <label className="font-semibold">Categoría<select required value={formulario.categoriaId || ''} onChange={(e) => setFormulario({ ...formulario, categoriaId: Number(e.target.value) })} className="mt-1 block w-full rounded border p-2 font-normal"><option value="" disabled>Elegí una categoría</option>{categorias.map((c) => <option key={c.id} value={c.id}>{c.nombre}</option>)}</select></label>
                    <label className="font-semibold sm:col-span-2">Descripción<textarea maxLength={500} value={formulario.descripcion} onChange={(e) => setFormulario({ ...formulario, descripcion: e.target.value })} className="mt-1 block w-full rounded border p-2 font-normal" rows={3} /></label>
                    <label className="font-semibold sm:col-span-2">URLs de imágenes (una por línea)<textarea value={formulario.imagenes.join('\n')} onChange={(e) => setFormulario({ ...formulario, imagenes: e.target.value.split('\n').map((url) => url.trim()).filter(Boolean) })} className="mt-1 block w-full rounded border p-2 font-normal" rows={3} /></label>
                    {error && <p role="alert" className="text-red-700 sm:col-span-2">{error}</p>}
                    <div className="flex gap-3 sm:col-span-2">
                        <button disabled={guardando} className="rounded-lg bg-brand-800 px-5 py-3 font-semibold text-white disabled:opacity-60">{guardando ? 'Guardando...' : editando ? 'Guardar cambios' : 'Crear producto'}</button>
                        {editando && <button type="button" onClick={() => { setEditando(null); setFormulario(formularioVacio) }} className="rounded-lg bg-brand-100 px-5 py-3 font-semibold">Cancelar edición</button>}
                    </div>
                </form>

                <ul className="mt-6 space-y-3">
                    {productos.map((producto) => (
                        <li key={producto.id} className="flex flex-wrap items-center justify-between gap-3 rounded-xl bg-white p-4 shadow">
                            <div><strong>{producto.nombre}</strong><p className="text-sm text-brand-700">Stock: {producto.stock} · ${producto.precio}</p></div>
                            <div className="flex gap-2"><button onClick={() => prepararEdicion(producto)} className="rounded bg-brand-100 px-3 py-2 font-semibold">Editar</button><button onClick={() => void eliminar(producto)} className="rounded bg-red-100 px-3 py-2 font-semibold text-red-800">Dar de baja</button></div>
                        </li>
                    ))}
                </ul>
            </section>
        </main>
    )
}

export default AdminProductos
