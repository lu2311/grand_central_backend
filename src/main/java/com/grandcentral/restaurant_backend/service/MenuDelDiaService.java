package com.GrandCentral.restaurant_backend.service;

import com.GrandCentral.restaurant_backend.model.MenuDelDia;
import com.GrandCentral.restaurant_backend.repository.MenuDelDiaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
public class MenuDelDiaService {

    private final MenuDelDiaRepository repo;

    public MenuDelDiaService(MenuDelDiaRepository repo) {
        this.repo = repo;
    }

    public MenuDelDia crear(MenuDelDia menu) {
        return repo.save(menu);
    }

    public List<MenuDelDia> listarTodos() {
        return repo.findAll();
    }

    public MenuDelDia buscarPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menú no encontrado."));
    }

    public MenuDelDia actualizar(Long id, MenuDelDia actualizado) {
        MenuDelDia m = buscarPorId(id);
        m.setNombre(actualizado.getNombre());
        m.setTipo(actualizado.getTipo());
        m.setFecha(actualizado.getFecha());
        return repo.save(m);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menú no encontrado.");
        }
        repo.deleteById(id);
    }

    public List<MenuDelDia> buscarPorTipo(String tipo) {
        return repo.findByTipoIgnoreCase(tipo);
    }

    public List<MenuDelDia> buscarPorFecha(LocalDate fecha) {
        return repo.findByFecha(fecha);
    }
}
