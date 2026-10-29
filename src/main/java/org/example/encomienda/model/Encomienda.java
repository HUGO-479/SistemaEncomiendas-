package org.example.encomienda.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.agencia.model.Agencia;
import org.example.bulto.model.Bulto;
import org.example.cliente.model.Cliente;
import org.example.usuario.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "encomienda")
@Data // <--- Esta es la que crea los set y get
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Encomienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoSeguimiento;

    @Enumerated(EnumType.STRING)
    private CanalRegistro canal;

    @Enumerated(EnumType.STRING)
    private EstadoEncomienda estado;

    @ManyToOne
    @JoinColumn(name = "remitente_id")
    private Cliente remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Cliente destinatario;

    @ManyToOne
    @JoinColumn(name = "agencia_origen_id")
    private Agencia agenciaOrigen;

    @ManyToOne
    @JoinColumn(name = "agencia_destino_id")
    private Agencia agenciaDestino;

    @ManyToOne
    @JoinColumn(name = "registrado_por_usuario_id")
    private Usuario registradoPorUsuario;

    private BigDecimal costoTotal;

    private LocalDateTime fechaRegistro;

    // --- ESTOS SON LOS QUE FALTABAN ---
    private LocalDateTime fechaRecepcion;

    @ManyToOne
    @JoinColumn(name = "empleado_recepcion_id")
    private Usuario empleadoRecepcion;
    // ---------------------------------

    private Boolean anulado;

    @OneToMany(mappedBy = "encomienda", cascade = CascadeType.ALL)
    private List<Bulto> bultos;

    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) this.fechaRegistro = LocalDateTime.now();
        if (this.anulado == null) this.anulado = false;
    }
}