
import axios from 'axios'

// Dirección base del backend Spring Boot.
const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
})

// Interceptor que agrega automáticamente el JWT.
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token')

    // Si existe un token, lo enviamos en Authorization.
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
})

// Una respuesta 401 significa que el token ya no se acepta; el proveedor limpia la sesión global.
api.interceptors.response.use(
    (respuesta) => respuesta,
    (error: unknown) => {
        if (axios.isAxiosError(error) && error.response?.status === 401) {
            window.dispatchEvent(new Event('sesion-expirada'))
        }
        return Promise.reject(error)
    }
)

export default api
