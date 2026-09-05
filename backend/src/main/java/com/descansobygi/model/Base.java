package com.descansobygi.model;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
@Setter
@MappedSuperclass
@SuperBuilder(toBuilder = true)
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreate = LocalDateTime.now();

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private boolean eliminado = false;

}
