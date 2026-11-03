
package com.descansobygi.config;

import com.descansobygi.security.JsonErrorHandlers;
import com.descansobygi.security.JwtAuthenticationFilter;
import com.descansobygi.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // CSRF no aplica: la API no usa cookies de sesión, usa tokens JWT.
                .csrf(csrf -> csrf.disable())

                // Habilita CORS para permitir solicitudes desde el frontend.
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Sin sesión en el servidor: cada request se autentica con su token.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configura qué rutas pueden utilizarse sin autenticación.
                .authorizeHttpRequests(auth -> auth
                        // Swagger y la documentación OpenAPI.
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Registro y login: se usan antes de tener un token.
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/registrar", "/api/auth/login").permitAll()

                        // Ruta interna de Spring para errores; si se bloquea,
                        // cualquier error se ve como 401.
                        .requestMatchers("/error").permitAll()

                        // Todo lo demás requiere un token JWT válido.
                        .anyRequest().authenticated()
                )

                // Respuestas JSON para 401 (sin token / token inválido) y 403 (sin permiso).
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(JsonErrorHandlers.noAutenticado())
                        .accessDeniedHandler(JsonErrorHandlers.accesoDenegado())
                )

                // El filtro JWT corre antes que el filtro estándar de usuario/contraseña.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)

                // Desactiva el formulario de login y HTTP Basic: el login es POST /api/auth/login.
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
    // Define qué orígenes pueden comunicarse con el backend.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Dirección donde se ejecuta nuestro frontend React.
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        // Métodos HTTP permitidos.
        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        // Encabezados permitidos, incluido Authorization para JWT.
        configuration.setAllowedHeaders(
                List.of("Content-Type", "Authorization")
        );

        // Permite exponer encabezados de respuesta si los necesitamos.
        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        // No utilizamos cookies de sesión; JWT se envía mediante Authorization.
        configuration.setAllowCredentials(false);

        // Aplica esta configuración a todos los endpoints.
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // Cifra las contraseñas con BCrypt (registro) y las compara (login).
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
