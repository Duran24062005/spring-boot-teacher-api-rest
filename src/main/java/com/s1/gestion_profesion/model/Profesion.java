package com.s1.gestion_profesion.model;


import jakarta.persistence.*;
import lombok.*;

/**
 * PRIMERO:
 * Se necesita Mappear la base de datos, para que, JPA identifique cuales son
 * las entidades que va a manipular.
*/

@Entity
@Table(name = "profesion")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Profesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
}
