import { createContext } from 'react'
import type { Carrito, ItemCarritoRequest } from '../types/carrito'

export interface CarritoContextType {
    carrito: Carrito | null
    cargando: boolean
    error: string
    cantidadTotal: number
    total: number
    recargar: () => Promise<void>
    agregar: (item: ItemCarritoRequest) => Promise<boolean>
    cambiarCantidad: (itemId: number, cantidad: number) => Promise<boolean>
    quitar: (itemId: number) => Promise<boolean>
    vaciar: () => Promise<boolean>
}

export const CarritoContext = createContext<CarritoContextType | undefined>(undefined)
