// Datos que necesita la pantalla para agregar un producto al carrito.
export interface ItemCarritoRequest {
    productoId: number
    cantidad: number
}

// Ítem del carrito tal como lo devuelve el backend.
export interface ItemCarrito {
    id: number
    cantidad: number
    subtotal: number
    carritoId: number
    productoId: number
    productoNombre: string
    productoUnitario: number
}

// Estados posibles del carrito (enum EstadoCarrito del backend).
export type EstadoCarrito = 'ACTIVO' | 'ELIMINADO'

// Carrito completo con sus ítems.
export interface Carrito {
    id: number
    estado: EstadoCarrito
    usuarioId: number
    items: ItemCarrito[]
}