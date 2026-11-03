
import { useState, type FormEvent } from 'react'
import { registrarUsuario } from '../services/usuarioService'
import type { UsuarioRequest } from '../types/usuario'

function Registro() {
    // Estado de los campos del formulario.
    const [formulario, setFormulario] = useState<UsuarioRequest>({
        nombre: '',
        apellido: '',
        mail: '',
        contrasena: '',
        celular: '',
    })

    // Estados para controlar el envío y los mensajes.
    const [cargando, setCargando] = useState(false)
    const [mensaje, setMensaje] = useState('')
    const [error, setError] = useState('')

    // Actualiza cada campo del formulario.
    const manejarCambio = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target

        setFormulario((anterior) => ({
            ...anterior,
            [name]: value,
        }))
    }

    // Envía los datos al backend.
    const manejarEnvio = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        setMensaje('')
        setError('')
        setCargando(true)

        try {
            const datos: UsuarioRequest = {
                ...formulario,
                nombre: formulario.nombre.trim(),
                apellido: formulario.apellido.trim(),
                mail: formulario.mail.trim(),
                celular: formulario.celular?.trim() || undefined,
            }

            const usuario = await registrarUsuario(datos)

            setMensaje(`¡Bienvenido/a, ${usuario.nombre}! Tu cuenta fue creada correctamente.`)

            // Limpiamos el formulario después del registro exitoso.
            setFormulario({
                nombre: '',
                apellido: '',
                mail: '',
                contrasena: '',
                celular: '',
            })
        } catch (err: unknown) {
            // Mostramos un mensaje según el error recibido.
            if (typeof err === 'object' && err !== null && 'response' in err) {
                setError('No se pudo completar el registro. Verificá los datos o si el correo ya está registrado.')
            } else {
                setError('No se pudo conectar con el servidor. Intentá nuevamente.')
            }
        } finally {
            setCargando(false)
        }
    }

    return (
        <main className="min-h-screen bg-brand-950 flex items-center justify-center px-4 py-10">

            <section className="w-full max-w-lg rounded-2xl bg-brand-100 p-8 shadow-2xl">

                <div className="mb-8 text-center">
                    <p className="mb-2 text-sm font-medium uppercase tracking-[0.25em] text-brand-700">
                        Descanso by Gi
                    </p>

                    <h1 className="text-3xl font-bold text-brand-950">
                        Crear cuenta
                    </h1>

                    <p className="mt-2 text-sm text-brand-700">
                        Registrate para disfrutar de nuestros productos.
                    </p>
                </div>

                <form onSubmit={manejarEnvio} className="space-y-4">

                    <div>
                        <label htmlFor="nombre" className="mb-1 block text-sm font-medium text-brand-950">
                            Nombre
                        </label>

                        <input
                            id="nombre"
                            name="nombre"
                            type="text"
                            value={formulario.nombre}
                            onChange={manejarCambio}
                            maxLength={100}
                            required
                            className="w-full rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                            placeholder="Ingresá tu nombre"
                        />
                    </div>

                    <div>
                        <label htmlFor="apellido" className="mb-1 block text-sm font-medium text-brand-950">
                            Apellido
                        </label>

                        <input
                            id="apellido"
                            name="apellido"
                            type="text"
                            value={formulario.apellido}
                            onChange={manejarCambio}
                            maxLength={100}
                            required
                            className="w-full rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                            placeholder="Ingresá tu apellido"
                        />
                    </div>

                    <div>
                        <label htmlFor="mail" className="mb-1 block text-sm font-medium text-brand-950">
                            Correo electrónico
                        </label>

                        <input
                            id="mail"
                            name="mail"
                            type="email"
                            value={formulario.mail}
                            onChange={manejarCambio}
                            maxLength={150}
                            required
                            className="w-full rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                            placeholder="ejemplo@correo.com"
                        />
                    </div>

                    <div>
                        <label htmlFor="contrasena" className="mb-1 block text-sm font-medium text-brand-950">
                            Contraseña
                        </label>

                        <input
                            id="contrasena"
                            name="contrasena"
                            type="password"
                            value={formulario.contrasena}
                            onChange={manejarCambio}
                            minLength={4}
                            maxLength={10}
                            required
                            className="w-full rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                            placeholder="Entre 4 y 10 caracteres"
                        />
                    </div>

                    <div>
                        <label htmlFor="celular" className="mb-1 block text-sm font-medium text-brand-950">
                            Celular (opcional)
                        </label>

                        <input
                            id="celular"
                            name="celular"
                            type="tel"
                            value={formulario.celular}
                            onChange={manejarCambio}
                            maxLength={30}
                            className="w-full rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                            placeholder="Tu número de teléfono"
                        />
                    </div>

                    {mensaje && (
                        <p role="status" className="rounded-lg bg-brand-300 p-3 text-sm text-brand-950">
                            {mensaje}
                        </p>
                    )}

                    {error && (
                        <p role="alert" className="rounded-lg bg-red-100 p-3 text-sm text-red-800">
                            {error}
                        </p>
                    )}

                    <button
                        type="submit"
                        disabled={cargando}
                        className="w-full rounded-lg bg-brand-800 px-4 py-3 font-semibold text-white transition hover:bg-brand-700 disabled:cursor-not-allowed disabled:opacity-60"
                    >
                        {cargando ? 'Registrando...' : 'Crear cuenta'}
                    </button>

                </form>
            </section>
        </main>
    )
}

export default Registro
