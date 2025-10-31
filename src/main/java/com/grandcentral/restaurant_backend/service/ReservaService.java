package com.grandcentral.restaurant_backend.service;


import com.grandcentral.restaurant_backend.model.Reserva;
import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.repository.ReservaRepository;
import com.grandcentral.restaurant_backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository repo;
    private final UsuarioRepository usuarioRepo;

    public ReservaService(ReservaRepository repo, UsuarioRepository usuarioRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
    }

    public Reserva crear(Reserva reserva) {
        Usuario usuario = usuarioRepo.findById(reserva.getUsuario().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

        // Validar que no tenga reserva activa
        if (repo.findByUsuarioId(usuario.getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya tiene una reserva activa.");
        }

        reserva.setHoraReserva(LocalDateTime.now());
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

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada.");
        }
        repo.deleteById(id);
    }
}
