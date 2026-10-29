package org.example.encomienda.repository;

import org.example.encomienda.model.Encomienda;
import org.example.encomienda.model.EstadoEncomienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncomiendaRepository extends JpaRepository<Encomienda, Long> {

    // Spring Data JPA leerá el nombre de este método y creará la consulta SQL automáticamente:
    // SELECT * FROM encomienda WHERE remitente.numero_documento = ? AND estado = ?
    List<Encomienda> findByRemitenteNumeroDocumentoAndEstado(String numeroDocumento, EstadoEncomienda estado);

    List<Encomienda> findByRemitenteOrDestinatario(org.example.cliente.model.Cliente remitente, org.example.cliente.model.Cliente destinatario);

}
