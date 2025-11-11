package com.grandcentral.restaurant_backend.service;

import com.grandcentral.restaurant_backend.model.MenuDelDia;
import com.grandcentral.restaurant_backend.model.Plato;
import com.grandcentral.restaurant_backend.model.Reserva;
import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.repository.MenuDelDiaRepository;
import com.grandcentral.restaurant_backend.repository.PlatoRepository;
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
    private final PlatoRepository platoRepo;
    private final MenuDelDiaRepository menuRepo;

    public ReservaService(ReservaRepository repo, UsuarioRepository usuarioRepo, PlatoRepository platoRepo, MenuDelDiaRepository menuRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.platoRepo = platoRepo;
        this.menuRepo = menuRepo;
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

        // Validar que el usuario haya elegido o un plato o un menú, pero no ambos
        boolean tienePlato = reserva.getPlato() != null;
        boolean tieneMenu = reserva.getMenu() != null;

        if (tienePlato == tieneMenu) { // ambos true o ambos false
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Debes seleccionar solo un tipo de reserva: un plato o un menú del día.");
        }

        if (tienePlato) {
            // Validar que el plato exista
            Plato plato = platoRepo.findById(reserva.getPlato().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado."));
            reserva.setPlato(plato);
        }

        if (tieneMenu) {
            // Validar que el menú exista y sea el del día de la reserva
            MenuDelDia menu = menuRepo.findById(reserva.getMenu().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menú no encontrado."));
            reserva.setMenu(menu);
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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "No puedes eliminar una reserva que no te pertenece.");
        }

        repo.delete(reserva);
    }
}
