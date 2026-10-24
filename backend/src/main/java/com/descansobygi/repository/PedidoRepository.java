
package com.descansobygi.repository;

import com.descansobygi.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona las operaciones CRUD basicas para los pedidos
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
