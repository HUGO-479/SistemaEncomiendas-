package org.example.recepcion.service;

import org.example.envio.model.Admision;
import org.example.envio.model.Envio;
import org.example.envio.model.EstadoEnvio;
import org.example.envio.repository.EnvioRepository;
import org.example.persona.model.Persona;
import org.example.persona.repository.PersonaRepository;
import org.example.usuario.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RecepcionService {

    private final EnvioRepository envioRepository;
    private final PersonaRepository personaRepository;

    public RecepcionService(EnvioRepository envioRepository, PersonaRepository personaRepository) {
        this.envioRepository = envioRepository;
        this.personaRepository = personaRepository;
    }

    // Busca si la persona (remitente) tiene envíos pendientes (REGISTRADO)
    public List<Envio> buscarPendientesPorDni(String dni) {
        // Buscamos a la persona por su DNI
        Persona persona = personaRepository.findByTipoDocumentoAndNumeroDocumento(org.example.persona.model.TipoDocumento.DNI, dni)
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        // Retornamos los envíos en estado REGISTRADO
        return envioRepository.findByRemitenteOrDestinatario(persona, persona)
                .stream()
                .filter(e -> e.getEstado() == EstadoEnvio.REGISTRADO)
                .toList();
    }

    // Confirma la recepción, pesa el paquete y cambia el estado
    @Transactional
    public void confirmarRecepcion(Long envioId, Double pesoFinal, Long tipoPaqueteId, Usuario empleado) {
        // Buscamos el Envio en lugar de la Encomienda
        Envio envio = envioRepository.findById(envioId)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));

        // 1. Cambiar estado a EN_TRANSITO (o el que corresponda después de la agencia)
        // Nota: En la nueva BD, el estado después de agencia es EN_TRANSITO, o podemos usar DISPONIBLE_RECOJO más adelante.
        envio.setEstado(EstadoEnvio.EN_TRANSITO);

        // 2. Actualizar el peso en la Admision asociada
        Admision admision = envio.getAdmision();
        if (admision != null) {
            admision.setPesoKg(BigDecimal.valueOf(pesoFinal));
            // Aquí podrías actualizar también la decisión a ACEPTADA
            admision.setDecision(org.example.envio.model.DecisionAdmision.ACEPTADA);
        }

        // 3. Guardar los cambios
        envioRepository.save(envio);
    }
}