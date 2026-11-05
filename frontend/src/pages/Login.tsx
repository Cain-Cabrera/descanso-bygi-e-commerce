
import { useState, type FormEvent } from 'react'
import axios from 'axios'
import { useNavigate, Link } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'
import { iniciarSesion } from '../services/authService'
import type { LoginRequest } from '../types/auth'

function Login() {
    const navigate = useNavigate()

    const { iniciarSesionContext } = useAuth()

    // Datos ingresados por el usuario.
    const [formulario, setFormulario] = useState<LoginRequest>({
        mail: '',
        contrasena: '',
    })

    // Estados del formulario.
    const [cargando, setCargando] = useState(false)
    const [error, setError] = useState('')

    // Actualiza los campos.
    const manejarCambio = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target

        setFormulario((anterior) => ({
            ...anterior,
            [name]: value,
        }))
    }

    // Envía las credenciales al backend.
    const manejarEnvio = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        setError('')
        setCargando(true)

        try {
            const datos: LoginRequest = {
                mail: formulario.mail.trim(),
                contrasena: formulario.contrasena,
            }

            const respuesta = await iniciarSesion(datos)

            // Centralizamos el almacenamiento de la sesión.
            iniciarSesionContext(respuesta)

            // Redirige al inicio después del login.
            navigate('/')

        } catch (err: unknown) {

            if (axios.isAxiosError(err)) {
                const status = err.response?.status

                if (status === 401) {
                    setError('Correo electrónico o contraseña incorrectos.')
                } else if (status === 400) {
                    setError('Verificá los datos ingresados.')
                } else {
                    setError('No se pudo iniciar sesión. Intentá nuevamente.')
                }

            } else {
                setError('No se pudo conectar con el servidor.')
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
                        Iniciar sesión
                    </h1>

                    <p className="mt-2 text-sm text-brand-700">
                        Ingresá a tu cuenta para continuar.
                    </p>
                </div>

                <form onSubmit={manejarEnvio} className="space-y-5">

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
                            required
                            className="w-full rounded-lg border border-brand-300 bg-white px-4 py-3 text-brand-950 outline-none focus:border-brand-700 focus:ring-2 focus:ring-brand-300"
                            placeholder="Ingresá tu contraseña"
                        />
                    </div>

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
                        {cargando ? 'Ingresando...' : 'Iniciar sesión'}
                    </button>

                    <p className="text-center text-sm text-brand-700">
                        ¿Todavía no tenés cuenta?{' '}
                        <Link to="/registro" className="font-semibold text-brand-800 underline">
                            Registrate
                        </Link>
                    </p>

                </form>

            </section>
        </main>
    )
}

export default Login
