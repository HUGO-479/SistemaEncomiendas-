package org.example.usuario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;  // TINYINT UNSIGNED

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    private String descripcion;
}