import {
    createContext,
    useCallback,
    useContext,
    useEffect,
    useMemo,
    useState,
    type ReactNode,
} from 'react'

import { useAuth } from './AuthContext'
import {
    agregarAlCarrito,
    eliminarDelCarrito,
    modificarCantidad,
    obtenerCarrito,
    vaciarCarrito,
} from '../services/carritoService'
import type { Carrito, ItemCarritoRequest } from '../types/carrito'

// Define los datos y acciones disponibles en el contexto.
interface CarritoContextType {
    carrito: Carrito | null
    cargando: boolean
    error: string
    cantidadTotal: number
    total: number
    agregar: (item: ItemCarritoRequest) => Promise<boolean>
    cambiarCantidad: (itemId: number, cantidad: number) => Promise<boolean>
    quitar: (itemId: number) => Promise<boolean>
    vaciar: () => Promise<boolean>
}

const CarritoContext = createContext<CarritoContextType | undefined>(undefined)

// Proveedor que administra el estado global del carrito.
export function CarritoProvider({ children }: { children: ReactNode }) {
    const { usuario } = useAuth()

    const [carrito, setCarrito] = useState<Carrito | null>(null)
    const [cargando, setCargando] = useState(false)
    const [error, setError] = useState('')

    // Vuelve a pedir el carrito al backend para mostrar siempre lo real.
    const recargar = useCallback(async () => {
        if (!usuario) {
            setCarrito(null)
            return
        }

        try {
            setCarrito(await obtenerCarrito(usuario.id))
        } catch (err) {
            console.error('Error al cargar el carrito:', err)
            setError('No se pudo cargar el carrito.')
        }
    }, [usuario])

    // Carga el carrito cuando hay sesión y lo limpia al cerrarla.
    useEffect(() => {
        const cargarInicial = async () => {
            setCargando(true)
            await recargar()
            setCargando(false)
        }

        cargarInicial()
    }, [recargar])

    // Ejecuta una acción sobre el carrito y refresca el estado.
    // Devuelve true si salió bien, para que la pantalla pueda reaccionar.
    const ejecutar = useCallback(
        async (accion: () => Promise<void>, mensajeError: string) => {
            setError('')

            try {
                await accion()
                await recargar()
                return true
            } catch (err) {
                console.error(mensajeError, err)
                setError(mensajeError)
                return false
            }
        },
        [recargar]
    )

    const agregar = (item: ItemCarritoRequest) =>
        usuario
            ? ejecutar(
                  () => agregarAlCarrito(usuario.id, item),
                  'No se pudo agregar el producto. Revisá el stock disponible.'
              )
            : Promise.resolve(false)

    const cambiarCantidad = (itemId: number, cantidad: number) =>
        usuario
            ? ejecutar(
                  () => modificarCantidad(usuario.id, itemId, cantidad),
                  'No se pudo cambiar la cantidad. Revisá el stock disponible.'
              )
            : Promise.resolve(false)

    const quitar = (itemId: number) =>
        usuario
            ? ejecutar(
                  () => eliminarDelCarrito(usuario.id, itemId),
                  'No se pudo quitar el producto.'
              )
            : Promise.resolve(false)

    const vaciar = () =>
        usuario
            ? ejecutar(
                  () => vaciarCarrito(usuario.id),
                  'No se pudo vaciar el carrito.'
              )
            : Promise.resolve(false)

    // Valores derivados: contador del header y total a pagar.
    const items = carrito?.items ?? []

    const cantidadTotal = useMemo(
        () => items.reduce((suma, item) => suma + item.cantidad, 0),
        [items]
    )

    const total = useMemo(
        () => items.reduce((suma, item) => suma + item.subtotal, 0),
        [items]
    )

    return (
        <CarritoContext.Provider
            value={{
                carrito,
                cargando,
                error,
                cantidadTotal,
                total,
                agregar,
                cambiarCantidad,
                quitar,
                vaciar,
            }}
        >
            {children}
        </CarritoContext.Provider>
    )
}

// Hook para acceder fácilmente al contexto.
export function useCarrito() {
    const contexto = useContext(CarritoContext)

    if (!contexto) {
        throw new Error('useCarrito debe utilizarse dentro de CarritoProvider')
    }

    return contexto
}