package org.example.cliente.service;

import org.example.cliente.dto.ClienteRegistroDTO;
import org.example.cliente.model.Cliente;
import org.example.cliente.model.TipoDocumento;
import org.example.cliente.repository.ClienteRepository;
import org.example.shared.util.ValidadorDocumento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Optional<Cliente> buscarPorDocumento(TipoDocumento tipo, String numero) {
        return clienteRepository.findByTipoDocumentoAndNumeroDocumento(tipo, numero);
    }

    @Transactional
    public Cliente registrarCliente(ClienteRegistroDTO dto) {
        // Validamos el formato
        ValidadorDocumento.validar(dto.getTipoDocumento(), dto.getNumeroDocumento());

        // Verificamos si ya existe
        Optional<Cliente> existente = buscarPorDocumento(dto.getTipoDocumento(), dto.getNumeroDocumento());
        if (existente.isPresent()) {
            return existente.get();
        }

        // Convertimos el DTO a Entidad
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setTipoDocumento(dto.getTipoDocumento());
        nuevoCliente.setNumeroDocumento(dto.getNumeroDocumento());
        nuevoCliente.setNombres(dto.getNombres());
        nuevoCliente.setApellidos(dto.getApellidos());
        nuevoCliente.setRazonSocial(dto.getRazonSocial());
        nuevoCliente.setCelular(dto.getCelular());
        nuevoCliente.setDireccion(dto.getDireccion());

        return clienteRepository.save(nuevoCliente);
    }
}