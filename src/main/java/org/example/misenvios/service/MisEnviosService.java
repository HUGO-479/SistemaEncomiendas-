package org.example.misenvios.service;

import org.example.encomienda.model.Encomienda;
import org.example.encomienda.repository.EncomiendaRepository;
import org.example.usuario.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MisEnviosService {

    private final EncomiendaRepository encomiendaRepository;

    public MisEnviosService(EncomiendaRepository encomiendaRepository) {
        this.encomiendaRepository = encomiendaRepository;
    }

    public List<Encomienda> obtenerEnviosDelCliente(Usuario clienteLogueado) {
        // Buscamos las encomiendas donde el cliente sea Remitente o Destinatario
        // (Necesitamos agregar este método en el EncomiendaRepository)
        return encomiendaRepository.findByRemitenteOrDestinatario(clienteLogueado.getCliente(), clienteLogueado.getCliente());
    }
}
