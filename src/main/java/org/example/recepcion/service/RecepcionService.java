package org.example.recepcion.service;

import org.example.bulto.model.Bulto;
import org.example.cliente.repository.ClienteRepository;
import org.example.encomienda.model.Encomienda;
import org.example.encomienda.model.EstadoEncomienda;
import org.example.encomienda.repository.EncomiendaRepository;
import org.example.tipopaquete.repository.TipoPaqueteRepository;
import org.example.usuario.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecepcionService {

    private final EncomiendaRepository encomiendaRepository;
    private final ClienteRepository clienteRepository;
    private final TipoPaqueteRepository tipoPaqueteRepository;

    public RecepcionService(EncomiendaRepository encomiendaRepository, ClienteRepository clienteRepository, TipoPaqueteRepository tipoPaqueteRepository) {
        this.encomiendaRepository = encomiendaRepository;
        this.clienteRepository = clienteRepository;
        this.tipoPaqueteRepository = tipoPaqueteRepository;
    }

    // Busca si el cliente (remitente) tiene envíos pendientes (PRE_REGISTRADO)
    public List<Encomienda> buscarPendientesPorDni(String dni) {
        return encomiendaRepository.findByRemitenteNumeroDocumentoAndEstado(dni, EstadoEncomienda.PRE_REGISTRADO);
    }

    // Confirma la recepción, pesa el bulto y cambia el estado a EN_AGENCIA
    @Transactional // Si algo falla, no se guarda nada a medias en la BD
    public void confirmarRecepcion(Long encomiendaId, Double pesoFinal, Long tipoPaqueteId, Usuario empleado) {
        Encomienda encomienda = encomiendaRepository.findById(encomiendaId)
                .orElseThrow(() -> new RuntimeException("Encomienda no encontrada"));

        // 1. Cambiar estado
        encomienda.setEstado(EstadoEncomienda.EN_AGENCIA);
        encomienda.setFechaRecepcion(LocalDateTime.now());
        encomienda.setEmpleadoRecepcion(empleado);

        // 2. Actualizar el peso del primer bulto (asumiendo que solo hay 1 para este ejemplo)
        if (!encomienda.getBultos().isEmpty()) {
            Bulto bulto = encomienda.getBultos().get(0);
            bulto.setPesoFinal(BigDecimal.valueOf(pesoFinal));
            bulto.setTipoPaquete(tipoPaqueteRepository.findById(tipoPaqueteId).orElse(null));

            // 3. Calcular el costo (simplificado: base 10 + 2 por kilo)
            BigDecimal costo = BigDecimal.valueOf(10.00).add(BigDecimal.valueOf(pesoFinal).multiply(BigDecimal.valueOf(2.00)));
            bulto.setCosto(costo);
            encomienda.setCostoTotal(costo);
        }

        encomiendaRepository.save(encomienda);
    }
}
