package org.example.agencia.service;

import org.example.agencia.model.Agencia;
import org.example.agencia.repository.AgenciaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Le dice a Spring que esta clase contiene la lógica de negocio
public class AgenciaService {
    private final AgenciaRepository agenciaRepository;

    public AgenciaService(AgenciaRepository agenciaRepository) {
        this.agenciaRepository = agenciaRepository;
    }

    public List<Agencia> listarTodas() {
        return agenciaRepository.findAll();
    }

    public Agencia guardar(Agencia agencia) {
        // Aquí podrías agregar validaciones, por ejemplo: que el nombre no esté vacío
        return agenciaRepository.save(agencia);
    }
}
