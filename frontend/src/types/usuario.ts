// datos que enviamos al backend para registrar un usuario
export interface UsuarioRequest {
    nombre: string
    apellido: string
    mail: string
    contrasena: string
    celular?: string
}

// Ddatos que recibimos del backend despues del registro
export interface UsuarioResponse {
    id: number
    nombre: string
    apellido: string
    mail: string
    celular: string | null
}