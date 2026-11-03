
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

export default api
