package org.example.bulto.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "bulto")
@Data
public class Bulto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encomienda_id", nullable = false)
    private org.example.encomienda.model.Encomienda encomienda;

    @Column(name = "numero_bulto", nullable = false)
    private Integer numeroBulto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_paquete_id", nullable = false)
    private org.example.tipopaquete.model.TipoPaquete tipoPaquete;

    private String descripcion;

    @Column(name = "peso_estimado")
    private BigDecimal pesoEstimado;

    @Column(name = "peso_final")
    private BigDecimal pesoFinal;

    private BigDecimal costo;
}
