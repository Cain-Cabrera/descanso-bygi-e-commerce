import api from './api'
import type { Carrito, ItemCarritoRequest } from '../types/carrito'

// Obtiene el carrito activo del usuario.
export const obtenerCarrito = async (): Promise<Carrito> => {
    const respuesta = await api.get<Carrito>('/api/carrito')
    return respuesta.data
}

// Agrega un producto al carrito.
// El backend identifica el carrito usando el usuario autenticado en el token,
// por eso el body solo necesita el producto y la cantidad.
export const agregarAlCarrito = async (
    item: ItemCarritoRequest
): Promise<void> => {
    await api.post('/api/carrito/items', {
        productoId: item.productoId,
        cantidad: item.cantidad,
    })
}

// Cambia la cantidad de un ítem.
export const modificarCantidad = async (
    itemId: number,
    cantidad: number
): Promise<void> => {
    await api.patch(`/api/carrito/items/${itemId}`, null, {
        params: { cantidad },
    })
}

// Elimina un ítem del carrito.
export const eliminarDelCarrito = async (
    itemId: number
): Promise<void> => {
    await api.delete(`/api/carrito/items/${itemId}`)
}

// Vacía el carrito completo.
export const vaciarCarrito = async (): Promise<void> => {
    await api.delete('/api/carrito')
}
