
package com.descansobygi.repository.pedidos;

import com.descansobygi.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona operaciones CRUD para los detalles de los pedidos.
public interface DetallePedidoRepository
        extends JpaRepository<DetallePedido, Long> {
}
