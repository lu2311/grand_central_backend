package com.grandcentral.restaurant_backend.service;


import com.grandcentral.restaurant_backend.model.Reserva;
import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.repository.ReservaRepository;
import com.grandcentral.restaurant_backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.*;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository repo;
    private final UsuarioRepository usuarioRepo;

    public ReservaService(ReservaRepository repo, UsuarioRepository usuarioRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
    }

    public Reserva crear(Reserva reserva, String correoUsuario) {
        Usuario usuario = usuarioRepo.findByCorreo(correoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

        // Obtener hora actual
        ZoneId zonaPeru = ZoneId.of("America/Lima");
        LocalDateTime ahora = LocalDateTime.now(zonaPeru);
        LocalTime limite = LocalTime.of(8, 0);

        // Determinar fecha de reserva (día actual o siguiente)
        LocalDate fechaReserva = ahora.toLocalTime().isBefore(limite)
                ? ahora.toLocalDate()
                : ahora.toLocalDate().plusDays(1);

        // Validar si ya tiene una reserva para esa fecha
        if (repo.findByUsuarioIdAndFechaReserva(usuario.getId(), fechaReserva).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya tienes una reserva para esa fecha.");
        }

        // Solo permitir reservas de plato a la carta (por ahora)
        if (reserva.getPlato() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debes seleccionar un plato para la reserva.");
        }

        reserva.setUsuario(usuario);
        reserva.setFechaReserva(fechaReserva);
        reserva.setHoraReserva(ahora);

        return repo.save(reserva);
    }

    public List<Reserva> listarTodas() {
        return repo.findAll();
    }

    public Reserva buscarPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada."));
    }

    public List<Reserva> listarPorUsuario(Usuario usuario) {
        return repo.findByUsuario(usuario);
    }

    public void eliminar(Long reservaId, String correoUsuario) {
        Usuario usuario = usuarioRepo.findByCorreo(correoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

        Reserva reserva = repo.findById(reservaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada."));

        if (!reserva.getUsuario().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes eliminar una reserva que no te pertenece.");
        }

        repo.delete(reserva);
    }
}
