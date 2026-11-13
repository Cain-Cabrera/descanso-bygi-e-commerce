import api from './api'
import type { Producto } from '../types/producto'
import type { Pedido } from '../types/pedido'
import type { UsuarioResponse } from '../types/usuario'

export interface CategoriaAdmin { id: number; nombre: string; descripcion: string | null }
export interface MarcaAdmin { id: number; nombre: string }
export interface ProductoAdminInput {
    nombre: string
    descripcion: string
    precio: number
    stock: number
    marcaId: number
    categoriaId: number
    imagenes: string[]
}
export interface CategoriaAdminInput { nombre: string; descripcion: string }
export interface MarcaAdminInput { nombre: string }
export interface UsuarioAdminInput { nombre: string; apellido: string; mail: string; celular: string }

// Estas funciones usan los endpoints protegidos para ADMINISTRADOR en SecurityConfig.
export const adminService = {
    productos: async () => (await api.get<Producto[]>('/api/catalogo/producto')).data,
    crearProducto: async (datos: ProductoAdminInput) => (await api.post<Producto>('/api/catalogo/producto/crear', datos)).data,
    editarProducto: async (id: number, datos: ProductoAdminInput) => (await api.patch<Producto>(`/api/catalogo/producto/${id}`, datos)).data,
    eliminarProducto: async (id: number) => api.delete(`/api/catalogo/producto/${id}`),

    categorias: async () => (await api.get<CategoriaAdmin[]>('/api/catalogo/categoria')).data,
    crearCategoria: async (datos: CategoriaAdminInput) => (await api.post<CategoriaAdmin>('/api/catalogo/categoria', datos)).data,
    editarCategoria: async (id: number, datos: Partial<CategoriaAdminInput>) => (await api.patch<CategoriaAdmin>(`/api/catalogo/categoria/${id}`, datos)).data,
    eliminarCategoria: async (id: number) => api.delete(`/api/catalogo/categoria/${id}`),

    marcas: async () => (await api.get<MarcaAdmin[]>('/api/catalogo/marca')).data,
    crearMarca: async (datos: MarcaAdminInput) => (await api.post<MarcaAdmin>('/api/catalogo/marca', datos)).data,
    editarMarca: async (id: number, datos: MarcaAdminInput) => (await api.patch<MarcaAdmin>(`/api/catalogo/marca/${id}`, datos)).data,
    eliminarMarca: async (id: number) => api.delete(`/api/catalogo/marca/${id}`),

    usuarios: async () => (await api.get<UsuarioResponse[]>('/api/usuarios')).data,
    editarUsuario: async (id: number, datos: UsuarioAdminInput) => (await api.patch<UsuarioResponse>(`/api/usuarios/${id}`, datos)).data,
    eliminarUsuario: async (id: number) => api.delete(`/api/usuarios/${id}`),

    pedidos: async () => (await api.get<Pedido[]>('/api/pedidos')).data,
    cancelarPedido: async (id: number) => api.delete(`/api/pedidos/${id}`),
}
