package com.descansobygi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// Filtro que se ejecuta una vez en cada request, antes de llegar al controller.
// Si la request trae un token válido, marca al usuario como autenticado.
// No se anota con @Component: se crea en SecurityConfig para que
// Spring Boot no lo registre dos veces.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String PREFIJO_BEARER = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Leer el header "Authorization: Bearer <token>".
        String token = extraerToken(request);

        // 2. Si hay token y es válido, cargar el usuario en el SecurityContext.
        if (token != null && jwtTokenProvider.validarToken(token)) {
            Long usuarioId = jwtTokenProvider.obtenerUsuarioId(token);
            String rol = jwtTokenProvider.obtenerRol(token);

            // Spring Security trabaja los roles con el prefijo "ROLE_".
            List<SimpleGrantedAuthority> permisos = List.of(new SimpleGrantedAuthority("ROLE_" + rol));

            // principal = id del usuario; credenciales = null (ya se validó el token).
            UsernamePasswordAuthenticationToken autenticacion =
                    new UsernamePasswordAuthenticationToken(usuarioId, null, permisos);

            // 3. A partir de acá, Spring considera autenticada esta request.
            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        }

        // 4. Continuar con la cadena. Si no había token válido, el request sigue
        // sin autenticar y SecurityConfig decide si la ruta lo permite o devuelve 401.
        filterChain.doFilter(request, response);
    }

    private String extraerToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(PREFIJO_BEARER)) {
            return header.substring(PREFIJO_BEARER.length()).trim();
        }
        return null;
    }
}
