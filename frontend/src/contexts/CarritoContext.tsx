import {
    useCallback,
    useEffect,
    useState,
    type ReactNode,
} from 'react'

import { useAuth } from './useAuth'
import {
    agregarAlCarrito,
    eliminarDelCarrito,
    modificarCantidad,
    obtenerCarrito,
    vaciarCarrito,
} from '../services/carritoService'
import type { Carrito, ItemCarritoRequest } from '../types/carrito'
import { CarritoContext } from './carritoState'

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
            setCarrito(await obtenerCarrito())
        } catch (err) {
            console.error('Error al cargar el carrito:', err)
            setError('No se pudo cargar el carrito.')
            throw err
        }
    }, [usuario])

    // Carga el carrito cuando hay sesión y lo limpia al cerrarla.
    useEffect(() => {
        const cargarInicial = async () => {
            setCargando(true)
            try {
                await recargar()
            } catch {
                // recargar ya registra el error visible para la interfaz.
            } finally {
                setCargando(false)
            }
        }

        cargarInicial()
    }, [recargar])

    // Ejecuta una acción sobre el carrito y refresca el estado.
    // Devuelve true si salió bien, para que la pantalla pueda reaccionar.
    const ejecutar = useCallback(
        async (accion: () => Promise<void>, mensajeError: string) => {
            setError('')
            setCargando(true)

            try {
                await accion()
                await recargar()
                return true
            } catch (err) {
                console.error(mensajeError, err)
                setError(mensajeError)
                return false
            } finally {
                setCargando(false)
            }
        },
        [recargar]
    )

    const agregar = (item: ItemCarritoRequest) =>
        usuario
            ? ejecutar(
                  () => agregarAlCarrito(item),
                  'No se pudo agregar el producto. Revisá el stock disponible.'
              )
            : Promise.resolve(false)

    const cambiarCantidad = (itemId: number, cantidad: number) =>
        usuario
            ? ejecutar(
                  () => modificarCantidad(itemId, cantidad),
                  'No se pudo cambiar la cantidad. Revisá el stock disponible.'
              )
            : Promise.resolve(false)

    const quitar = (itemId: number) =>
        usuario
            ? ejecutar(
                  () => eliminarDelCarrito(itemId),
                  'No se pudo quitar el producto.'
              )
            : Promise.resolve(false)

    const vaciar = () =>
        usuario
            ? ejecutar(
                  () => vaciarCarrito(),
                  'No se pudo vaciar el carrito.'
              )
            : Promise.resolve(false)

    // Valores derivados: contador del header y total a pagar.
    const items = carrito?.items ?? []
    const cantidadTotal = items.reduce((suma, item) => suma + item.cantidad, 0)
    const total = items.reduce((suma, item) => suma + item.subtotal, 0)

    return (
        <CarritoContext.Provider
            value={{
                carrito,
                cargando,
                error,
                cantidadTotal,
                total,
                recargar,
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
