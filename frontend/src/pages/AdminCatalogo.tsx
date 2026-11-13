import { useEffect, useState, type FormEvent } from 'react'
import { Link } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { adminService, type CategoriaAdmin, type MarcaAdmin } from '../services/adminService'

// Categorías y marcas se administran por separado porque tienen reglas y datos distintos.
function AdminCatalogo() {
    const [categorias, setCategorias] = useState<CategoriaAdmin[]>([])
    const [marcas, setMarcas] = useState<MarcaAdmin[]>([])
    const [categoriaId, setCategoriaId] = useState<number | null>(null)
    const [marcaId, setMarcaId] = useState<number | null>(null)
    const [categoria, setCategoria] = useState({ nombre: '', descripcion: '' })
    const [marca, setMarca] = useState({ nombre: '' })
    const [error, setError] = useState('')

    const cargar = async () => {
        try {
            const [cs, ms] = await Promise.all([adminService.categorias(), adminService.marcas()])
            setCategorias(cs); setMarcas(ms)
        } catch { setError('No se pudieron cargar categorías y marcas.') }
    }
    useEffect(() => {
        let vigente = true
        Promise.all([adminService.categorias(), adminService.marcas()])
            .then(([cs, ms]) => { if (vigente) { setCategorias(cs); setMarcas(ms) } })
            .catch(() => { if (vigente) setError('No se pudieron cargar categorías y marcas.') })
        return () => { vigente = false }
    }, [])

    const guardarCategoria = async (event: FormEvent) => {
        event.preventDefault(); setError('')
        try {
            if (categoriaId) await adminService.editarCategoria(categoriaId, categoria)
            else await adminService.crearCategoria(categoria)
            setCategoria({ nombre: '', descripcion: '' }); setCategoriaId(null); await cargar()
        } catch { setError('No se pudo guardar la categoría. Revisá si el nombre ya existe.') }
    }
    const guardarMarca = async (event: FormEvent) => {
        event.preventDefault(); setError('')
        try {
            if (marcaId) await adminService.editarMarca(marcaId, marca)
            else await adminService.crearMarca(marca)
            setMarca({ nombre: '' }); setMarcaId(null); await cargar()
        } catch { setError('No se pudo guardar la marca. Revisá si el nombre ya existe.') }
    }
    const eliminarCategoria = async (item: CategoriaAdmin) => {
        if (!window.confirm(`¿Dar de baja la categoría “${item.nombre}”?`)) return
        try { await adminService.eliminarCategoria(item.id); await cargar() }
        catch { setError('No se puede dar de baja: puede haber productos activos asociados.') }
    }
    const eliminarMarca = async (item: MarcaAdmin) => {
        if (!window.confirm(`¿Dar de baja la marca “${item.nombre}”?`)) return
        try { await adminService.eliminarMarca(item.id); await cargar() }
        catch { setError('No se puede dar de baja: puede haber productos activos asociados.') }
    }

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-6xl px-6 py-10">
                <Link to="/admin" className="font-semibold text-brand-800 underline">← Panel</Link>
                <h1 className="mt-4 text-3xl font-bold text-brand-950">Categorías y marcas</h1>
                {error && <p role="alert" className="mt-4 rounded bg-red-100 p-3 text-red-800">{error}</p>}
                <div className="mt-6 grid gap-6 lg:grid-cols-2">
                    <section className="rounded-xl bg-white p-5 shadow">
                        <h2 className="text-xl font-bold">Categorías</h2>
                        <form onSubmit={(e) => void guardarCategoria(e)} className="mt-4 space-y-3">
                            <input required maxLength={100} aria-label="Nombre de categoría" placeholder="Nombre" value={categoria.nombre} onChange={(e) => setCategoria({ ...categoria, nombre: e.target.value })} className="w-full rounded border p-2" />
                            <textarea maxLength={255} aria-label="Descripción de categoría" placeholder="Descripción" value={categoria.descripcion} onChange={(e) => setCategoria({ ...categoria, descripcion: e.target.value })} className="w-full rounded border p-2" />
                            <button className="rounded bg-brand-800 px-4 py-2 font-semibold text-white">{categoriaId ? 'Guardar categoría' : 'Crear categoría'}</button>
                            {categoriaId && <button type="button" onClick={() => { setCategoriaId(null); setCategoria({ nombre: '', descripcion: '' }) }} className="ml-2 underline">Cancelar</button>}
                        </form>
                        <ul className="mt-5 space-y-2">{categorias.map((item) => <li key={item.id} className="flex justify-between gap-3 border-t pt-3"><span>{item.nombre}</span><span className="flex gap-2"><button onClick={() => { setCategoriaId(item.id); setCategoria({ nombre: item.nombre, descripcion: item.descripcion ?? '' }) }} className="underline">Editar</button><button onClick={() => void eliminarCategoria(item)} className="text-red-700 underline">Baja</button></span></li>)}</ul>
                    </section>
                    <section className="rounded-xl bg-white p-5 shadow">
                        <h2 className="text-xl font-bold">Marcas</h2>
                        <form onSubmit={(e) => void guardarMarca(e)} className="mt-4 flex gap-2">
                            <input required maxLength={100} aria-label="Nombre de marca" placeholder="Nombre" value={marca.nombre} onChange={(e) => setMarca({ nombre: e.target.value })} className="min-w-0 flex-1 rounded border p-2" />
                            <button className="rounded bg-brand-800 px-4 py-2 font-semibold text-white">{marcaId ? 'Guardar' : 'Crear'}</button>
                        </form>
                        {marcaId && <button type="button" onClick={() => { setMarcaId(null); setMarca({ nombre: '' }) }} className="mt-2 underline">Cancelar edición</button>}
                        <ul className="mt-5 space-y-2">{marcas.map((item) => <li key={item.id} className="flex justify-between gap-3 border-t pt-3"><span>{item.nombre}</span><span className="flex gap-2"><button onClick={() => { setMarcaId(item.id); setMarca({ nombre: item.nombre }) }} className="underline">Editar</button><button onClick={() => void eliminarMarca(item)} className="text-red-700 underline">Baja</button></span></li>)}</ul>
                    </section>
                </div>
            </section>
        </main>
    )
}

export default AdminCatalogo
