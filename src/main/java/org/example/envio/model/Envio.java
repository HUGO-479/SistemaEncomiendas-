package org.example.envio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.persona.model.Persona;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "envios")
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 40)
    private String numeroGuia;

    @OneToOne
    @JoinColumn(name = "admision_id", nullable = false, unique = true)
    private Admision admision;

    @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private Persona remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Persona destinatario;

    @Column(nullable = false)
    private BigDecimal importeCotizado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEnvio estado = EstadoEnvio.REGISTRADO;

    @Column(name = "registrado_en", updatable = false)
    private LocalDateTime registradoEn;

    // (Faltarían las relaciones a agenciaOrigen y agenciaDestino)

    @PrePersist
    protected void onCreate() {
        this.registradoEn = LocalDateTime.now();
    }

}