package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.Votacion;
import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.model.MenuDelDia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VotacionRepository extends JpaRepository<Votacion, Long> {
    Optional<Votacion> findByUsuarioAndFechaVoto(Usuario usuario, LocalDate fecha);
    List<Votacion> findByMenu(MenuDelDia menu);
}
