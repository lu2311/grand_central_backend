package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.VotacionDelDia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface VotacionDelDiaRepository extends JpaRepository<VotacionDelDia, Long> {
    Optional<VotacionDelDia> findByFecha(LocalDate fecha);
}
