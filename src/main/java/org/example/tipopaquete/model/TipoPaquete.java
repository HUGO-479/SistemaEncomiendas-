package org.example.tipopaquete.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipo_paquete")
@Data
public class TipoPaquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Boolean activo = true;
}
