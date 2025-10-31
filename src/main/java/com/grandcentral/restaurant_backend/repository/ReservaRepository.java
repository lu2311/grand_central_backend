package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.Reserva;
import com.grandcentral.restaurant_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Buscar todas las reservas de un usuario
    List<Reserva> findByUsuario(Usuario usuario);

    // Verificar si el usuario ya tiene una reserva activa
    Optional<Reserva> findByUsuarioId(Long usuarioId);
}
