package com.grandcentral.restaurant_backend.service;

import com.grandcentral.restaurant_backend.model.MenuDelDia;
import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.model.Votacion;
import com.grandcentral.restaurant_backend.repository.MenuDelDiaRepository;
import com.grandcentral.restaurant_backend.repository.UsuarioRepository;
import com.grandcentral.restaurant_backend.repository.VotacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class VotacionService {

    private final VotacionRepository votacionRepo;
    private final UsuarioRepository usuarioRepo;
    private final MenuDelDiaRepository menuRepo;

    public VotacionService(VotacionRepository votacionRepo, UsuarioRepository usuarioRepo, MenuDelDiaRepository menuRepo) {
        this.votacionRepo = votacionRepo;
        this.usuarioRepo = usuarioRepo;
        this.menuRepo = menuRepo;
    }

    public Votacion votar(Long usuarioId, Long menuId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        MenuDelDia menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menú no encontrado"));

        // Verificar si ya votó hoy
        if (votacionRepo.findByUsuarioAndFechaVoto(usuario, LocalDate.now()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya realizaste tu voto hoy");
        }

        Votacion votacion = new Votacion();
        votacion.setUsuario(usuario);
        votacion.setMenu(menu);
        votacion.setFechaVoto(LocalDate.now());

        return votacionRepo.save(votacion);
    }

    public List<Votacion> listarTodas() {
        return votacionRepo.findAll();
    }

    public long contarVotosPorMenu(Long menuId) {
        MenuDelDia menu = menuRepo.findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menú no encontrado"));
        return votacionRepo.findByMenu(menu).size();
    }
}
