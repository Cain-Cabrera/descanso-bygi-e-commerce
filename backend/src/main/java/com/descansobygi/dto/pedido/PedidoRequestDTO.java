package com.descansobygi.dto.pedido;

import com.descansobygi.enums.FormaDePago;
import jakarta.validation.constraints.NotNull;

// recibe los datos necesarios para solicitar un pedido
// el usuario y el total los determina en el backend

public record PedidoRequestDTO(

        @NotNull(message = "La forma de pago es obligatorio")
        FormaDePago formaDePago
) {
}
