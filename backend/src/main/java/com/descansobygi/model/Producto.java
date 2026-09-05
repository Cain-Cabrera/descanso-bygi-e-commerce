package com.descansobygi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"nombre"}, callSuper = false)
@SuperBuilder
@Entity
@Table(name = "Productos")
public class Producto extends Base {

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 300)
    private String descripcion;
    @Column(nullable = false)
    private BigDecimal precio;

    @Column(nullable = false)
    private int stock;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false)
    private String marca;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Imagen> imagenes;
}
