
export interface Producto {
    id: number
    nombre: string
    descripcion: string | null
    precio: number
    stock: number
    marcaId: number
    marcaNombre: string
    categoriaId: number
    categoriaNombre: string
    imagenes: string[]
}


