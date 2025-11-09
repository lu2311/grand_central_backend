package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.model.VotoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.*;

public interface VotoUsuarioRepository extends JpaRepository<VotoUsuario, Long> {
    Optional<VotoUsuario> findByUsuarioAndFechaVoto(Usuario usuario, LocalDate fecha);
    List<VotoUsuario> findByEntradaIdInOrFondoIdIn(List<Long> entradaIds, List<Long> fondoIds);
}