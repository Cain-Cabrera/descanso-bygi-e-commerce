
package com.descansobygi.repository.pedidos;

import com.descansobygi.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

// Proporciona las operaciones CRUD basicas para los pedidos
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioIdAndEliminadoFalse(Long usuarioId);

}
