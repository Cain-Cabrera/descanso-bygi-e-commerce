import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { adminService } from '../services/adminService'

const secciones = [
    { ruta: '/admin/productos', titulo: 'Productos', detalle: 'Alta, edición, baja y stock' },
    { ruta: '/admin/catalogo', titulo: 'Categorías y marcas', detalle: 'Organización del catálogo' },
    { ruta: '/admin/usuarios', titulo: 'Usuarios', detalle: 'Consulta, edición y baja lógica' },
    { ruta: '/admin/pedidos', titulo: 'Pedidos', detalle: 'Consulta y cancelación' },
]

// El panel agrupa los módulos administrativos que define el alcance del proyecto.
function AdminDashboard() {
    const [resumen, setResumen] = useState<number[] | null>(null)
    const [error, setError] = useState('')
    useEffect(() => {
        // Los indicadores se calculan a partir de los listados autorizados del backend.
        Promise.all([adminService.productos(), adminService.categorias(), adminService.marcas(), adminService.usuarios(), adminService.pedidos()])
            .then(([productos, categorias, marcas, usuarios, pedidos]) => setResumen([productos.length, categorias.length, marcas.length, usuarios.length, pedidos.filter((pedido) => !pedido.cancelado).length]))
            .catch(() => setError('No se pudo cargar el resumen. Verificá el rol ADMINISTRADOR.'))
    }, [])

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-6xl px-6 py-10">
                <h1 className="text-3xl font-bold text-brand-950">Administración</h1>
                <p className="mt-2 text-brand-700">Elegí el módulo que querés gestionar.</p>
                {error && <p role="alert" className="mt-4 text-red-700">{error}</p>}
                {resumen && <dl className="mt-6 grid grid-cols-2 gap-3 sm:grid-cols-3 lg:grid-cols-5">{['Productos', 'Categorías', 'Marcas', 'Usuarios', 'Pedidos activos'].map((label, index) => <div key={label} className="rounded-xl bg-white p-4 shadow"><dt className="text-sm text-brand-700">{label}</dt><dd className="mt-1 text-2xl font-bold text-brand-950">{resumen[index]}</dd></div>)}</dl>}
                <div className="mt-7 grid gap-5 sm:grid-cols-2">
                    {secciones.map((seccion) => (
                        <Link key={seccion.ruta} to={seccion.ruta} className="rounded-xl bg-white p-6 shadow transition hover:shadow-lg">
                            <h2 className="text-xl font-bold text-brand-950">{seccion.titulo}</h2>
                            <p className="mt-2 text-brand-700">{seccion.detalle}</p>
                        </Link>
                    ))}
                </div>
            </section>
        </main>
    )
}

export default AdminDashboard
