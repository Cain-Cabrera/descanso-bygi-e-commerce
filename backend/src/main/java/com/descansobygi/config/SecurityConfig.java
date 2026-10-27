
package com.descansobygi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Desactiva CSRF para facilitar las pruebas de la API REST desde Swagger.
                .csrf(csrf -> csrf.disable())

                // Configura qué rutas pueden utilizarse sin autenticación.
                .authorizeHttpRequests(auth -> auth
                        // Permite acceder a Swagger y a la documentación OpenAPI.
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Permite registrar usuarios sin iniciar sesión.
                        .requestMatchers("/api/usuarios/registrar").permitAll()

                        // Permite temporalmente el resto de los endpoints para las pruebas.
                        .anyRequest().permitAll()
                )

                // Desactiva el formulario de login predeterminado de Spring Security.
                .formLogin(form -> form.disable())

                // Desactiva la autenticación HTTP Basic para estas pruebas.
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
