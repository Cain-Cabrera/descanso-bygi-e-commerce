
package com.descansobygi.model;

import com.descansobygi.enums.Rol;
import jakarta.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

// Representa a un usuario registrado en la aplicación.
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"mail"},callSuper = false)
public class Usuario extends Base {

    // Nombre del usuario.
    @Column(nullable = false, length = 100)
    private String nombre;

    // Apellido del usuario.
    @Column(nullable = false, length = 100)
    private String apellido;

    // El correo es obligatorio y no puede repetirse.
    @Column(nullable = false, unique = true, length = 150)
    private String mail;

    // Guarda la contraseña cifrada, nunca en texto plano.
    @Column(nullable = false, length = 255)
    private String contrasena;

    // Número de contacto opcional.
    @Column(length = 30)
    private String celular;

    // El rol se almacena como texto en la base de datos.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<Carrito> carritos = new HashSet<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private Set<Pedido> pedidos = new HashSet<>();
}
