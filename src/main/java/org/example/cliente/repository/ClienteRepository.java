package org.example.cliente.repository;

import org.example.cliente.model.Cliente;
import org.example.cliente.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Para buscar si el cliente ya existe por DNI/RUC
    Optional<Cliente> findByTipoDocumentoAndNumeroDocumento(TipoDocumento tipoDocumento, String numeroDocumento);
}
