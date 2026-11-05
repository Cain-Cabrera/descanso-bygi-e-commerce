
import api from './api'
import type { Producto } from '../types/producto'

// Consulta el catálogo de productos al backend.
// Axios agrega automáticamente el JWT mediante el interceptor.
export const listarProductos = async (): Promise<Producto[]> => {

    const respuesta = await api.get<Producto[]>(
        '/api/catalogo/producto'
    )

    return respuesta.data
}
