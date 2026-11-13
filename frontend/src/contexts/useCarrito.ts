import { useContext } from 'react'
import { CarritoContext } from './carritoState'

// Hook con validación para evitar usar el carrito fuera de CarritoProvider.
export function useCarrito() {
    const contexto = useContext(CarritoContext)
    if (!contexto) throw new Error('useCarrito debe utilizarse dentro de CarritoProvider')
    return contexto
}
