package com.descansobygi.security;

import com.descansobygi.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

// Se encarga de todo lo relacionado al token JWT:
// generarlo en el login, validarlo en cada request y leer sus datos.
@Component
public class JwtTokenProvider {

    // Nombres de los datos extra (claims) que guardamos dentro del token.
    private static final String CLAIM_MAIL = "mail";
    private static final String CLAIM_ROL = "rol";

    // Clave secreta con la que se firma el token. Si alguien modifica el token,
    // la firma deja de coincidir y el token se rechaza.
    private final SecretKey clave;

    // Tiempo de vida del token en milisegundos.
    private final long expiracionMs;

    public JwtTokenProvider(@Value("${jwt.secret}") String secreto,
                            @Value("${jwt.expiration-minutes:60}") long expiracionMinutos) {
        byte[] bytesSecreto = secreto.getBytes(StandardCharsets.UTF_8);
        // HS256 exige una clave de al menos 256 bits (32 bytes).
        if (bytesSecreto.length < 32) {
            throw new IllegalStateException("jwt.secret debe tener al menos 32 caracteres");
        }
        this.clave = Keys.hmacShaKeyFor(bytesSecreto);
        this.expiracionMs = expiracionMinutos * 60 * 1000;
    }

    // 1. Generar token al hacer login.
    // subject = id del usuario; además guardamos mail y rol.
    public String generarToken(Usuario usuario) {
        Date ahora = new Date();
        Date vencimiento = new Date(ahora.getTime() + expiracionMs);

        return Jwts.builder()
                .subject(String.valueOf(usuario.getId()))
                .claim(CLAIM_MAIL, usuario.getMail())
                .claim(CLAIM_ROL, usuario.getRol().name())
                .issuedAt(ahora)
                .expiration(vencimiento)
                .signWith(clave)
                .compact();
    }

    // 2. Validar token: comprueba la firma y que no esté vencido.
    public boolean validarToken(String token) {
        try {
            leerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Firma inválida, token vencido, mal formado o vacío.
            return false;
        }
    }

    // 3. Extraer el id del usuario (guardado en el subject).
    public Long obtenerUsuarioId(String token) {
        return Long.valueOf(leerClaims(token).getSubject());
    }

    // Extrae el rol del usuario (ADMINISTRADOR o USUARIO).
    public String obtenerRol(String token) {
        return leerClaims(token).get(CLAIM_ROL, String.class);
    }

    // Duración del token en segundos, para informarla al frontend.
    public long getExpiracionSegundos() {
        return expiracionMs / 1000;
    }

    // Verifica la firma y devuelve el contenido del token.
    // Lanza JwtException si el token no es válido o está vencido.
    private Claims leerClaims(String token) {
        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
