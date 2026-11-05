import { useEffect, useState } from 'react'
import { listarProductos } from '../services/productoService'
import type { Producto } from '../types/producto'

// Carga los productos del backend y expone el estado de la consulta.
export function useProductos() {
    const [productos, setProductos] = useState<Producto[]>([])
    const [cargando, setCargando] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        const cargar = async () => {
            try {
                setProductos(await listarProductos())
            } catch (err) {
                console.error('Error al cargar productos:', err)
                setError('No se pudieron cargar los productos.')
            } finally {
                setCargando(false)
            }
        }

        cargar()
    }, [])

    return { productos, cargando, error }
}