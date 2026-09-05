package com.descansobygi.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "productos")
@EqualsAndHashCode(of = {"nombre"}, callSuper = false)
@Table(name = "categorías")
@SuperBuilder
public class Categoria extends Base {
    @Column(nullable = false, length = 60)
    private String nombre;

    @Column(length = 200)
    private String descripcion;

    @OneToMany(mappedBy = "categoría", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Producto> productos;

    @ManyToOne
    @JoinColumn(name = "id_imagen")
    private Imagen imagen;

    public void agregarProducto(Producto producto) {
        if (this.productos == null) {
            this.productos = new HashSet<>();
        }
        this.productos.add(producto);
        producto.setCategoria(this);
    }
}
