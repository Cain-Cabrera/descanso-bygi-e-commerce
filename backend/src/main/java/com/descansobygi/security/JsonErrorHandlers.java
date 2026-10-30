package com.descansobygi.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

// Respuestas JSON para los errores de seguridad, con el mismo formato
// que ErrorResponse (time, status, error, detalles).
// Hacen falta porque estos errores ocurren en los filtros, antes de llegar
// a los controllers, y por eso GlobalExceptionHandler no los ve.
public final class JsonErrorHandlers {

    private JsonErrorHandlers() {
    }

    // 401: no hay token, es inválido o está vencido.
    public static AuthenticationEntryPoint noAutenticado() {
        return (request, response, ex) -> escribir(response, HttpStatus.UNAUTHORIZED,
                "No autenticado", "Token ausente, inválido o vencido");
    }

    // 403: hay token válido pero el rol no tiene permiso.
    public static AccessDeniedHandler accesoDenegado() {
        return (request, response, ex) -> escribir(response, HttpStatus.FORBIDDEN,
                "Acceso denegado", "No tenés permisos para acceder a este recurso");
    }

    private static void escribir(HttpServletResponse response, HttpStatus status,
                                 String error, String detalle) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(String.format(
                "{\"time\":\"%s\",\"status\":%d,\"error\":\"%s\",\"detalles\":[\"%s\"]}",
                LocalDateTime.now(), status.value(), error, detalle));
    }
}
