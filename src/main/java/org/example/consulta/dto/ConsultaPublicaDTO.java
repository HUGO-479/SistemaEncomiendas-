package org.example.consulta.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ConsultaPublicaDTO {
    private String codigoSeguimiento;
    private String estadoActual;
    private String agenciaDestino;
    private LocalDateTime ultimaActualizacion;
    // OJO: No incluimos DNI ni celulares aquí (Regla de negocio)
}
