package org.example.persona.repository;

import org.example.persona.model.Persona;
import org.example.persona.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByTipoDocumentoAndNumeroDocumento(TipoDocumento tipo, String numero);
}