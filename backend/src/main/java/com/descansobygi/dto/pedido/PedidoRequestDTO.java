package com.descansobygi.dto.pedido;

import com.descansobygi.enums.FormaDePago;
import com.descansobygi.model.Pedido;
import com.descansobygi.model.Usuario;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// recibe los datos necesarios para solicitar un pedido
// el usuario y el total los determina en el backend

public record PedidoRequestDTO(

        @NotNull(message = "La forma de pago es obligatorio")
        FormaDePago formaDePago
) {
        public Pedido toEntidad(Usuario usuario, BigDecimal total) {
                Pedido pedido = new Pedido();
                pedido.setFormaDePago(this.formaDePago());
                pedido.setUsuario(usuario);
                pedido.setTotal(total);
                return pedido;
        }
}
