package com.descansobygi.service.pedidos;

import com.descansobygi.dto.pedido.PedidoRequestDTO;
import com.descansobygi.dto.pedido.PedidoResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPedidoService {

    @Transactional
    PedidoResponseDTO crearPedido(Long usuarioId, PedidoRequestDTO dto);

    @Transactional(readOnly = true)
    PedidoResponseDTO obtenerPorId(Long id);

    @Transactional(readOnly = true)
    List<PedidoResponseDTO> obtenerPedidosUsuario(Long usuarioId);

    @Transactional
    PedidoResponseDTO cancelarPedido(Long id);
}
