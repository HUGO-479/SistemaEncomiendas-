package org.example.usuario.repository;

import org.example.usuario.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Short> {
    Optional<Rol> findByNombre(String nombre);
}