package com.descansobygi.config;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // Nombre del esquema de seguridad que usa Swagger para el botón "Authorize".
    private static final String BEARER_AUTH = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Descanso by Gi - API")
                        .description("API REST de E-commerce de colchonería")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Descanso by Gi")
                                .url("https://descansobygi.com")))
                // Define el esquema JWT: Swagger agrega "Authorization: Bearer <token>" a cada request.
                .components(new Components()
                        .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                // Aplica el esquema a todos los endpoints (los públicos lo anulan con @SecurityRequirements).
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH));
    }
}
