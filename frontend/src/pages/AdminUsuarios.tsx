import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { adminService } from '../services/adminService'
import type { UsuarioResponse } from '../types/usuario'

// La edición administrativa comparte el DTO seguro de datos personales y nunca incluye contraseña ni rol.
function AdminUsuarios() {
    const [usuarios, setUsuarios] = useState<UsuarioResponse[]>([])
    const [editando, setEditando] = useState<number | null>(null)
    const [error, setError] = useState('')
    const cargar = async () => {
        try { setUsuarios(await adminService.usuarios()) }
        catch { setError('No se pudieron cargar los usuarios.') }
    }
    useEffect(() => {
        let vigente = true
        adminService.usuarios().then((lista) => { if (vigente) setUsuarios(lista) })
            .catch(() => { if (vigente) setError('No se pudieron cargar los usuarios.') })
        return () => { vigente = false }
    }, [])

    const guardar = async (event: React.FormEvent<HTMLFormElement>, usuario: UsuarioResponse) => {
        event.preventDefault(); setError('')
        const form = new FormData(event.currentTarget)
        try {
            await adminService.editarUsuario(usuario.id, {
                nombre: String(form.get('nombre') ?? ''), apellido: String(form.get('apellido') ?? ''),
                mail: String(form.get('mail') ?? ''), celular: String(form.get('celular') ?? ''),
            })
            setEditando(null); await cargar()
        } catch { setError('No se pudo guardar. Revisá los datos y que el correo sea único.') }
    }
    const eliminar = async (usuario: UsuarioResponse) => {
        if (!window.confirm(`¿Dar de baja a ${usuario.nombre} ${usuario.apellido}?`)) return
        try { await adminService.eliminarUsuario(usuario.id); await cargar() }
        catch { setError('No se pudo dar de baja el usuario.') }
    }

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-6xl px-6 py-10">
                <Link to="/admin" className="font-semibold text-brand-800 underline">← Panel</Link>
                <h1 className="mt-4 text-3xl font-bold text-brand-950">Gestión de usuarios</h1>
                {error && <p role="alert" className="mt-4 text-red-700">{error}</p>}
                <ul className="mt-6 space-y-3">{usuarios.map((usuario) => (
                    <li key={usuario.id} className="rounded-xl bg-white p-5 shadow">
                        {editando === usuario.id ? (
                            <form onSubmit={(event) => void guardar(event, usuario)} className="grid gap-3 sm:grid-cols-2">
                                <input name="nombre" aria-label="Nombre" defaultValue={usuario.nombre} required maxLength={100} className="rounded border p-2" />
                                <input name="apellido" aria-label="Apellido" defaultValue={usuario.apellido} required maxLength={100} className="rounded border p-2" />
                                <input name="mail" aria-label="Correo" type="email" defaultValue={usuario.mail} required maxLength={150} className="rounded border p-2" />
                                <input name="celular" aria-label="Celular" defaultValue={usuario.celular ?? ''} maxLength={30} className="rounded border p-2" />
                                <div className="flex gap-3 sm:col-span-2"><button className="rounded bg-brand-800 px-4 py-2 font-semibold text-white">Guardar</button><button type="button" onClick={() => setEditando(null)} className="underline">Cancelar</button></div>
                            </form>
                        ) : (
                            <div className="flex flex-wrap items-center justify-between gap-3"><div><strong>{usuario.nombre} {usuario.apellido}</strong><p className="text-sm text-brand-700">{usuario.mail} · {usuario.celular || 'Sin celular'}</p></div><div className="flex gap-3"><button onClick={() => setEditando(usuario.id)} className="font-semibold underline">Editar</button><button onClick={() => void eliminar(usuario)} className="font-semibold text-red-700 underline">Dar de baja</button></div></div>
                        )}
                    </li>
                ))}</ul>
            </section>
        </main>
    )
}

export default AdminUsuarios
