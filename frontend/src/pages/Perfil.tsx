import { useEffect, useState, type FormEvent } from 'react'
import Encabezado from '../components/Encabezado'
import { useAuth } from '../contexts/useAuth'
import { actualizarMiPerfil, obtenerMiPerfil } from '../services/usuarioService'
import type { UsuarioResponse } from '../types/usuario'

function Perfil() {
    const { actualizarPerfil } = useAuth()
    const [perfil, setPerfil] = useState<UsuarioResponse | null>(null)
    const [guardando, setGuardando] = useState(false)
    const [mensaje, setMensaje] = useState('')
    const [error, setError] = useState('')
    // El perfil se solicita al backend; no usamos localStorage como fuente de datos editable.
    useEffect(() => { obtenerMiPerfil().then(setPerfil).catch(() => setError('No se pudo cargar el perfil.')) }, [])

    // El DTO del backend solo acepta datos personales, así el cliente no puede cambiar su rol.
    const guardar = async (event: FormEvent) => {
        event.preventDefault()
        if (!perfil) return
        setGuardando(true); setError(''); setMensaje('')
        try {
            const actualizado = await actualizarMiPerfil({ nombre: perfil.nombre, apellido: perfil.apellido, mail: perfil.mail, celular: perfil.celular ?? '' })
            setPerfil(actualizado); actualizarPerfil(actualizado); setMensaje('Perfil actualizado.')
        } catch {
            setError('No se pudo guardar. Revisá los datos y que el correo no esté registrado por otra cuenta.')
        } finally { setGuardando(false) }
    }

    const campo = (nombre: keyof Pick<UsuarioResponse, 'nombre' | 'apellido' | 'mail' | 'celular'>, label: string, tipo = 'text') => (
        <label className="block font-semibold text-brand-950">
            {label}
            <input required={nombre !== 'celular'} type={tipo} value={perfil?.[nombre] ?? ''} onChange={(event) => setPerfil((actual) => actual ? { ...actual, [nombre]: event.target.value } : actual)} className="mt-2 block w-full rounded-lg border border-brand-300 px-4 py-3 font-normal" />
        </label>
    )

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-2xl px-6 py-10">
                <h1 className="text-3xl font-bold text-brand-950">Mi perfil</h1>
                {!perfil && !error && <p className="mt-5" role="status">Cargando perfil...</p>}
                {perfil && <form onSubmit={(event) => void guardar(event)} className="mt-6 space-y-4 rounded-xl bg-white p-6 shadow">
                    {campo('nombre', 'Nombre')}{campo('apellido', 'Apellido')}{campo('mail', 'Correo electrónico', 'email')}{campo('celular', 'Celular')}
                    {mensaje && <p role="status" className="text-green-800">{mensaje}</p>}{error && <p role="alert" className="text-red-700">{error}</p>}
                    <button disabled={guardando} className="rounded-lg bg-brand-800 px-6 py-3 font-semibold text-white disabled:opacity-60">{guardando ? 'Guardando...' : 'Guardar cambios'}</button>
                </form>}
                {error && !perfil && <p role="alert" className="mt-4 text-red-700">{error}</p>}
            </section>
        </main>
    )
}

export default Perfil
