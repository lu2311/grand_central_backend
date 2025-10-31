package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato, Long> {

    // Buscar por nombre (no obligatorio, pero útil)
    List<Plato> findByNombreContainingIgnoreCase(String nombre);
}
