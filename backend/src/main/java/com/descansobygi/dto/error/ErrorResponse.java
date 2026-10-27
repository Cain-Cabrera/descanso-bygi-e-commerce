package com.descansobygi.dto.error;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime time,
        int status,
        String error,
        List<String> detalles
) {
    public static ErrorResponse of(int status, String error,
                                   List<String> detalles) {
        return new ErrorResponse(LocalDateTime.now(),
                status, error, detalles);
    }

    public static ErrorResponse simple(int status, String error, String detalles) {
        return new ErrorResponse(LocalDateTime.now(), status, error, detalles != null ? List.of(detalles) : List.of());
    }
}
