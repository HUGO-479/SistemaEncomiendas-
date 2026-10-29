package org.example.encomienda.service;

import org.example.agencia.model.Agencia;
import org.example.agencia.repository.AgenciaRepository;
import org.example.bulto.model.Bulto;
import org.example.cliente.dto.ClienteRegistroDTO;
import org.example.cliente.model.Cliente;
import org.example.cliente.model.TipoDocumento;
import org.example.cliente.service.ClienteService;
import org.example.encomienda.dto.PreRegistroDTO;
import org.example.encomienda.model.CanalRegistro;
import org.example.encomienda.model.Encomienda;
import org.example.encomienda.model.EstadoEncomienda;
import org.example.encomienda.repository.EncomiendaRepository;
import org.example.shared.util.GeneradorCodigo;
import org.example.tipopaquete.model.TipoPaquete;
import org.example.tipopaquete.repository.TipoPaqueteRepository;
import org.example.usuario.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EncomiendaService {

    private final EncomiendaRepository encomiendaRepository;
    private final ClienteService clienteService;
    private final AgenciaRepository agenciaRepository;
    private final TipoPaqueteRepository tipoPaqueteRepository;

    public EncomiendaService(EncomiendaRepository encomiendaRepository, ClienteService clienteService, AgenciaRepository agenciaRepository, TipoPaqueteRepository tipoPaqueteRepository) {
        this.encomiendaRepository = encomiendaRepository;
        this.clienteService = clienteService;
        this.agenciaRepository = agenciaRepository;
        this.tipoPaqueteRepository = tipoPaqueteRepository;
    }

    @Transactional
    public Encomienda registrarPreRegistro(PreRegistroDTO dto, Usuario clienteLogueado) {
        if (dto.getAgenciaDestinoId().equals(dto.getAgenciaDestinoId())) {
            throw new RuntimeException("La agencia de origen y destino no pueden ser la misma.");
        }

        // Usamos el ClienteService para buscar o crear
        Cliente remitente = clienteService.buscarPorDocumento(TipoDocumento.DNI, dto.getRemitenteDni())
                .orElseGet(() -> {
                    ClienteRegistroDTO nuevoDto = new ClienteRegistroDTO();
                    nuevoDto.setTipoDocumento(TipoDocumento.DNI);
                    nuevoDto.setNumeroDocumento(dto.getRemitenteDni());
                    nuevoDto.setNombres(dto.getRemitenteNombres());
                    nuevoDto.setApellidos(dto.getRemitenteApellidos());
                    nuevoDto.setCelular(dto.getRemitenteCelular());
                    return clienteService.registrarCliente(nuevoDto); // Aquí envías el DTO
                });

        // --- DESTINATARIO ---
        Cliente destinatario = clienteService.buscarPorDocumento(TipoDocumento.DNI, dto.getDestinatarioDni())
                .orElseGet(() -> {
                    ClienteRegistroDTO nuevoDto = new ClienteRegistroDTO();
                    nuevoDto.setTipoDocumento(TipoDocumento.DNI);
                    nuevoDto.setNumeroDocumento(dto.getDestinatarioDni());
                    nuevoDto.setNombres(dto.getDestinatarioNombres());
                    nuevoDto.setApellidos(dto.getDestinatarioApellidos());
                    nuevoDto.setCelular(dto.getDestinatarioCelular());
                    return clienteService.registrarCliente(nuevoDto); // Aquí envías el DTO
                });

        Agencia origen = agenciaRepository.findById(dto.getAgenciaOrigenId()).orElseThrow();
        Agencia destino = agenciaRepository.findById(dto.getAgenciaDestinoId()).orElseThrow();
        TipoPaquete tipoPaquete = tipoPaqueteRepository.findById(dto.getTipoPaqueteId()).orElseThrow();

        Encomienda encomienda = new Encomienda();
        encomienda.setCodigoSeguimiento(GeneradorCodigo.generarCodigoSeguimiento());
        encomienda.setCanal(CanalRegistro.WEB);
        encomienda.setEstado(EstadoEncomienda.PRE_REGISTRADO);
        encomienda.setRemitente(remitente);
        encomienda.setDestinatario(destinatario);
        encomienda.setAgenciaOrigen(origen);
        encomienda.setAgenciaDestino(destino);
        encomienda.setRegistradoPorUsuario(clienteLogueado);
        encomienda.setAnulado(false);

        Bulto bulto = new Bulto();
        bulto.setEncomienda(encomienda);
        bulto.setNumeroBulto(1);
        bulto.setTipoPaquete(tipoPaquete);
        bulto.setDescripcion(dto.getDescripcionBulto());
        bulto.setPesoEstimado(BigDecimal.valueOf(dto.getPesoEstimado()));

        List<Bulto> bultos = new ArrayList<>();
        bultos.add(bulto);
        encomienda.setBultos(bultos);

        return encomiendaRepository.save(encomienda);
    }
}