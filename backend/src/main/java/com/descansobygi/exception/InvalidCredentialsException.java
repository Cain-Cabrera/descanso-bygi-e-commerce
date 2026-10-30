package com.descansobygi.exception;

// Se lanza cuando el mail o la contraseña del login no son correctos.
// Usamos el mismo mensaje en los dos casos para no revelar qué mails están registrados.
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Mail o contraseña incorrectos");
    }
}
