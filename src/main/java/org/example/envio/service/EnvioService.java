package org.example.envio.service;

import org.example.envio.dto.PreRegistroDTO;
import org.example.envio.model.Admision;
import org.example.envio.model.DecisionAdmision;
import org.example.envio.model.Envio;
import org.example.envio.model.EstadoEnvio;
import org.example.envio.repository.EnvioRepository;
import org.example.persona.model.Persona;
import org.example.persona.model.TipoDocumento;
import org.example.persona.repository.PersonaRepository;
import org.example.usuario.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final PersonaRepository personaRepository;

    public EnvioService(EnvioRepository envioRepository, PersonaRepository personaRepository) {
        this.envioRepository = envioRepository;
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Envio registrarPreRegistro(PreRegistroDTO dto, Usuario clienteLogueado) {

        // 1. Buscar o crear Remitente
        Persona remitente = personaRepository.findByTipoDocumentoAndNumeroDocumento(TipoDocumento.DNI, dto.getRemitenteDni())
                .orElseGet(() -> {
                    Persona p = new Persona();
                    p.setTipoDocumento(TipoDocumento.DNI);
                    p.setNumeroDocumento(dto.getRemitenteDni());
                    p.setNombres(dto.getRemitenteNombres());
                    p.setApellidos(dto.getRemitenteApellidos());
                    p.setTelefono(dto.getRemitenteCelular());
                    return personaRepository.save(p);
                });

        // 2. Buscar o crear Destinatario
        Persona destinatario = personaRepository.findByTipoDocumentoAndNumeroDocumento(TipoDocumento.DNI, dto.getDestinatarioDni())
                .orElseGet(() -> {
                    Persona p = new Persona();
                    p.setTipoDocumento(TipoDocumento.DNI);
                    p.setNumeroDocumento(dto.getDestinatarioDni());
                    p.setNombres(dto.getDestinatarioNombres());
                    p.setApellidos(dto.getDestinatarioApellidos());
                    p.setTelefono(dto.getDestinatarioCelular());
                    return personaRepository.save(p);
                });

        // 3. Crear la Admision
        Admision admision = new Admision();
        admision.setRemitente(remitente);
        admision.setContenidoDeclarado(dto.getDescripcionBulto());
        admision.setPesoKg(BigDecimal.valueOf(dto.getPesoEstimado()));
        // Dimensiones por defecto para el pre-registro (se corrigen en agencia)
        admision.setLargoCm(BigDecimal.ONE);
        admision.setAnchoCm(BigDecimal.ONE);
        admision.setAltoCm(BigDecimal.ONE);
        admision.setDecision(DecisionAdmision.PENDIENTE);
        admision.setFecha(LocalDateTime.now());

        // 4. Crear el Envio
        Envio envio = new Envio();
        envio.setNumeroGuia("ENC-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        envio.setAdmision(admision);
        envio.setRemitente(remitente);
        envio.setDestinatario(destinatario);
        envio.setImporteCotizado(BigDecimal.ZERO);
        envio.setEstado(EstadoEnvio.REGISTRADO);

        return envioRepository.save(envio);
    }
}