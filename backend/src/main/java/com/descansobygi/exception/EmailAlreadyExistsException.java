package com.descansobygi.exception;

// Excepción para indicar que un correo ya está registrado.
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}