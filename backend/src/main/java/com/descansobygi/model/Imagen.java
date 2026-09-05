package com.descansobygi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(of = {"url"}, callSuper = false)
@Entity
@Table(name = "imagenes")
public class Imagen extends Base {
    @Column(nullable = false, length = 500)
    private String url;

    @Column(nullable = false, length = 150)
    private String descripcion;


}
