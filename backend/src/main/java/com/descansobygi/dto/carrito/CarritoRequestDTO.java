package com.descansobygi.dto.carrito;


// dto para solicitar la creacion de un carrito
// el usuario propietario y el estado acutual seran determinados en el backend

import com.descansobygi.enums.EstadoCarrito;
import com.descansobygi.model.Carrito;
import com.descansobygi.model.Usuario;

public record CarritoRequestDTO(

) {
    public Carrito toEntidad(Usuario usuario) {
        Carrito carrito = new Carrito();
        carrito.setUsuario(usuario);
        carrito.setEstado(EstadoCarrito.ACTIVO);
        return carrito;
    }
}
