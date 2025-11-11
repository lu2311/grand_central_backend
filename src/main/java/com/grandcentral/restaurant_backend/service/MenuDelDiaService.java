package com.grandcentral.restaurant_backend.service;

// package com.grandcentral.restaurant_backend.service;

import com.grandcentral.restaurant_backend.model.MenuDelDia;
import com.grandcentral.restaurant_backend.repository.MenuDelDiaRepository;
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
        if (repo.findByFecha(menu.getFecha()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un menú para esa fecha");
        return repo.save(menu);
    }

    public List<MenuDelDia> listarTodos() {
        return repo.findAll();
    }

    public MenuDelDia buscarPorFecha(LocalDate fecha) {
        return repo.findByFecha(fecha)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menú no encontrado para la fecha"));
    }

    public void eliminarPorFecha(LocalDate fecha) {
        repo.findByFecha(fecha).ifPresent(repo::delete);
    }

    public MenuDelDia guardarDirecto(MenuDelDia menu) {
        return repo.save(menu);
    }
}
