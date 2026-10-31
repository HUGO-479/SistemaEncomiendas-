package org.example.envio.repository;

import org.example.envio.model.Envio;
import org.example.persona.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByNumeroGuia(String numeroGuia);
    List<Envio> findByRemitenteOrDestinatario(Persona remitente, Persona destinatario);
}