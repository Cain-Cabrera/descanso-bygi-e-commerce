package com.descansobygi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// Da acceso al usuario autenticado en la request actual.
// Los datos salen del token JWT que validó JwtAuthenticationFilter,
// nunca de la URL ni del body, por eso no se pueden falsificar.
public final class UsuarioActual {

    private static final String ROL_ADMIN = "ROLE_ADMINISTRADOR";

    private UsuarioActual() {
    }

    // Id del usuario logueado (el filtro lo guarda como "principal").
    public static Long id() {
        return (Long) autenticacion().getPrincipal();
    }

    // true si el usuario logueado tiene rol ADMINISTRADOR.
    public static boolean esAdmin() {
        return autenticacion().getAuthorities().stream()
                .anyMatch(permiso -> ROL_ADMIN.equals(permiso.getAuthority()));
    }

    private static Authentication autenticacion() {
        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
        // Solo pasa si se usa en un endpoint público: es un error de programación.
        if (autenticacion == null || !(autenticacion.getPrincipal() instanceof Long)) {
            throw new IllegalStateException("No hay un usuario autenticado en esta request");
        }
        return autenticacion;
    }
}
