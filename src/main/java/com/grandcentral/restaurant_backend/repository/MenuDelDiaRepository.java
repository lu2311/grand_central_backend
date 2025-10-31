package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.MenuDelDia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface MenuDelDiaRepository extends JpaRepository<MenuDelDia, Long> {

    // Buscar menús por tipo (entrada, fondo)
    List<MenuDelDia> findByTipoIgnoreCase(String tipo);

    // Buscar menús por fecha
    List<MenuDelDia> findByFecha(LocalDate fecha);
}
