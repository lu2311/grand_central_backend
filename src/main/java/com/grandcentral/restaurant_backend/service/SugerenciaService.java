package com.grandcentral.restaurant_backend.service;

import com.grandcentral.restaurant_backend.model.Sugerencia;
import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.repository.SugerenciaRepository;
import com.grandcentral.restaurant_backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SugerenciaService {

    private final SugerenciaRepository repo;
    private final UsuarioRepository usuarioRepo;

    public SugerenciaService(SugerenciaRepository repo, UsuarioRepository usuarioRepo) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
    }

    public Sugerencia crear(Sugerencia sugerencia, String correoUsuario) {
        Usuario usuario = usuarioRepo.findByCorreo(correoUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

        sugerencia.setUsuario(usuario);
        sugerencia.setFecha(LocalDateTime.now());
        return repo.save(sugerencia);
    }

    public List<Sugerencia> listarTodas() {
        return repo.findAll();
    }

    public Sugerencia buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sugerencia no encontrada."));
    }

    public List<Sugerencia> listarPorUsuario(Usuario usuario) {
        return repo.findByUsuario(usuario);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sugerencia no encontrada.");
        }
        repo.deleteById(id);
    }
}
