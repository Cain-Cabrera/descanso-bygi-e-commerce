
// Datos que enviamos al backend para iniciar sesión.
export interface LoginRequest {
    mail: string
    contrasena: string
}

// Datos que recibimos cuando el login es exitoso.
export interface LoginResponse {
    token: string
    tipo: string
    expiraEn: number
    id: number
    nombre: string
    apellido: string
    mail: string
    rol: string
}
