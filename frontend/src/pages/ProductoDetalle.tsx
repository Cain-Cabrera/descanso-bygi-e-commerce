import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import Encabezado from '../components/Encabezado'
import { useAuth } from '../contexts/useAuth'
import { useCarrito } from '../contexts/useCarrito'
import { obtenerProducto } from '../services/productoService'
import type { Producto } from '../types/producto'

const formatearPrecio = (valor: number) =>
    new Intl.NumberFormat('es-AR', {
        style: 'currency',
        currency: 'ARS',
        minimumFractionDigits: 0,
        maximumFractionDigits: 2,
    }).format(valor)

function ProductoDetalle() {
    const { id } = useParams()
    const { usuario } = useAuth()
    const { agregar } = useCarrito()
    const [producto, setProducto] = useState<Producto | null>(null)
    const [imagenSeleccionada, setImagenSeleccionada] = useState(0)
    const [cargando, setCargando] = useState(true)
    const [mensaje, setMensaje] = useState('')

    // La ficha se carga por ID y se ignora la respuesta si la persona cambia de ruta antes de recibirla.
    useEffect(() => {
        let vigente = true
        const cargar = async () => {
            const productoId = Number(id)
            if (!Number.isInteger(productoId) || productoId <= 0) {
                setMensaje('El producto solicitado no existe.')
                setCargando(false)
                return
            }

            try {
                const resultado = await obtenerProducto(productoId)
                if (vigente) setProducto(resultado)
            } catch {
                if (vigente) setMensaje('No se pudo encontrar el producto.')
            } finally {
                if (vigente) setCargando(false)
            }
        }
        void cargar()
        return () => { vigente = false }
    }, [id])

    // El arreglo vacío permite mostrar la ficha aunque el producto todavía no tenga imágenes.
    const imagenes = producto?.imagenes ?? []
    const sinStock = !producto || producto.stock <= 0

    // El backend vuelve a validar el stock al agregar, por eso el resultado siempre se confirma con la API.
    const manejarAgregar = async () => {
        if (!producto) return
        const ok = await agregar({ productoId: producto.id, cantidad: 1 })
        setMensaje(ok ? 'Producto agregado al carrito.' : 'No se pudo agregar. Revisá el stock disponible.')
    }

    return (
        <main className="min-h-screen bg-brand-100">
            <Encabezado />
            <section className="mx-auto max-w-6xl px-6 py-10">
                <Link to="/catalogo" className="text-sm font-semibold text-brand-800 hover:underline">
                    ← Volver al catálogo
                </Link>

                {cargando && <p className="mt-8" role="status">Cargando producto...</p>}
                {!cargando && !producto && <p className="mt-8" role="alert">{mensaje}</p>}

                {producto && (
                    <div className="mt-6 grid gap-8 rounded-2xl bg-white p-6 shadow-md md:grid-cols-2 md:p-10">
                        <div>
                            {imagenes[imagenSeleccionada] ? (
                                <img
                                    src={imagenes[imagenSeleccionada]}
                                    alt={producto.nombre}
                                    className="h-80 w-full rounded-xl bg-brand-100 object-contain"
                                />
                            ) : (
                                <div className="flex h-80 items-center justify-center rounded-xl bg-brand-100 text-brand-700">
                                    Sin imagen disponible
                                </div>
                            )}
                            {imagenes.length > 1 && (
                                <div className="mt-3 flex gap-2 overflow-x-auto">
                                    {imagenes.map((imagen, index) => (
                                        <button
                                            key={`${imagen}-${index}`}
                                            type="button"
                                            onClick={() => setImagenSeleccionada(index)}
                                            aria-label={`Ver imagen ${index + 1}`}
                                            aria-pressed={imagenSeleccionada === index}
                                            className="rounded-lg border-2 border-transparent aria-pressed:border-brand-700"
                                        >
                                            <img src={imagen} alt="" className="h-16 w-16 rounded-md object-cover" />
                                        </button>
                                    ))}
                                </div>
                            )}
                        </div>

                        <div className="flex flex-col items-start">
                            <p className="text-sm font-medium text-brand-700">
                                {producto.categoriaNombre} · {producto.marcaNombre}
                            </p>
                            <h1 className="mt-2 text-3xl font-bold text-brand-950">{producto.nombre}</h1>
                            <p className="mt-4 whitespace-pre-line text-brand-800">
                                {producto.descripcion || 'Consultanos para conocer más sobre este producto.'}
                            </p>
                            <p className="mt-6 text-3xl font-bold text-brand-900">{formatearPrecio(producto.precio)}</p>
                            <p className={`mt-2 text-sm ${sinStock ? 'text-red-700' : 'text-brand-700'}`}>
                                {sinStock ? 'Sin stock disponible' : `Stock disponible: ${producto.stock}`}
                            </p>

                            {usuario ? (
                                <button
                                    type="button"
                                    disabled={sinStock}
                                    onClick={() => void manejarAgregar()}
                                    className="mt-6 rounded-lg bg-brand-800 px-6 py-3 font-semibold text-white hover:bg-brand-700 disabled:cursor-not-allowed disabled:opacity-60"
                                >
                                    Agregar al carrito
                                </button>
                            ) : (
                                <Link to="/login" className="mt-6 rounded-lg bg-brand-800 px-6 py-3 font-semibold text-white hover:bg-brand-700">
                                    Iniciá sesión para comprar
                                </Link>
                            )}
                            {mensaje && producto && <p className="mt-3 text-sm text-brand-800" role="status">{mensaje}</p>}
                        </div>
                    </div>
                )}
            </section>
        </main>
    )
}

export default ProductoDetalle
