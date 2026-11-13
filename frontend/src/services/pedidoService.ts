import api from './api'
import type { FormaDePago, Pedido } from '../types/pedido'

export const crearPedido = async (formaDePago: FormaDePago): Promise<Pedido> => {
    const respuesta = await api.post<Pedido>('/api/pedidos', { formaDePago })
    return respuesta.data
}

export const listarMisPedidos = async (): Promise<Pedido[]> => {
    const respuesta = await api.get<Pedido[]>('/api/pedidos/mis-pedidos')
    return respuesta.data
}

export const obtenerPedido = async (id: number): Promise<Pedido> => {
    const respuesta = await api.get<Pedido>(`/api/pedidos/${id}`)
    return respuesta.data
}
