export type FormaDePago = 'EFECTIVO' | 'TARJETA' | 'TRANSFERENCIA'

// Línea del pedido con precio histórico, independiente de cambios posteriores al catálogo.
export interface DetallePedido {
    id: number
    cantidad: number
    precioUnitario: number
    subtotal: number
    pedidoId: number
    productoId: number
    productoNombre: string
}

// Resumen usado por checkout, confirmación e historial del cliente.
export interface Pedido {
    id: number
    total: number
    formaDePago: FormaDePago
    usuarioId: number
    fechaAlta: string
    cancelado: boolean
    detalles: DetallePedido[]
}
