import api from './api'
import type { Carrito, ItemCarritoRequest } from '../types/carrito'

// Obtiene el carrito activo del usuario.
export const obtenerCarrito = async (usuarioId: number): Promise<Carrito> => {
    const respuesta = await api.get<Carrito>(`/api/carrito/${usuarioId}`)
    return respuesta.data
}

// Agrega un producto al carrito.
// El backend actual exige carritoId en el body, así que primero lo
// obtenemos con el carrito del usuario. Si el backend deja de pedirlo,
// se puede borrar esa consulta y mandar solo productoId y cantidad.
export const agregarAlCarrito = async (
    usuarioId: number,
    item: ItemCarritoRequest
): Promise<void> => {
    const carrito = await obtenerCarrito(usuarioId)

    await api.post(`/api/carrito/${usuarioId}/items`, {
        carritoId: carrito.id,
        productoId: item.productoId,
        cantidad: item.cantidad,
    })
}

// Cambia la cantidad de un ítem.
export const modificarCantidad = async (
    usuarioId: number,
    itemId: number,
    cantidad: number
): Promise<void> => {
    await api.patch(`/api/carrito/${usuarioId}/items/${itemId}`, null, {
        params: { cantidad },
    })
}

// Elimina un ítem del carrito.
export const eliminarDelCarrito = async (
    usuarioId: number,
    itemId: number
): Promise<void> => {
    await api.delete(`/api/carrito/${usuarioId}/items/${itemId}`)
}

// Vacía el carrito completo.
export const vaciarCarrito = async (usuarioId: number): Promise<void> => {
    await api.delete(`/api/carrito/${usuarioId}`)
}