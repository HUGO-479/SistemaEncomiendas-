package org.example.agencia.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "agencias")
@Data
public class Agencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false, length = 60)
    private String departamento;

    @Column(nullable = false, length = 60)
    private String ciudad;

    private String direccion;
    private String telefono;

    @Column(nullable = false)
    private Boolean activa = true;
}
