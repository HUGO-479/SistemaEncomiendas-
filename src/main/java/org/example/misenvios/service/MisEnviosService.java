package org.example.misenvios.service;

import org.example.envio.model.Envio;
import org.example.envio.repository.EnvioRepository;
import org.example.usuario.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MisEnviosService {

    private final EnvioRepository envioRepository;

    public MisEnviosService(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public List<Envio> obtenerEnviosDelCliente(Usuario clienteLogueado) {
        // Buscamos los envíos donde la Persona sea Remitente o Destinatario
        return envioRepository.findByRemitenteOrDestinatario(clienteLogueado.getPersona(), clienteLogueado.getPersona());
    }
}
