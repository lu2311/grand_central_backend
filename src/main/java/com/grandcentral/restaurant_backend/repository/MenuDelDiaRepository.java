package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.MenuDelDia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface MenuDelDiaRepository extends JpaRepository<MenuDelDia, Long> {
    Optional<MenuDelDia> findByFecha(LocalDate fecha);
}