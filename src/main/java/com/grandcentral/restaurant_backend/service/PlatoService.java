package com.grandcentral.restaurant_backend.service;

import com.grandcentral.restaurant_backend.model.Plato;
import com.grandcentral.restaurant_backend.repository.PlatoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class PlatoService {

    private final PlatoRepository repo;

    public PlatoService(PlatoRepository repo) {
        this.repo = repo;
    }

    public Plato crear(Plato plato) {
        return repo.save(plato);
    }

    public List<Plato> listarTodos() {
        return repo.findAll();
    }

    public Plato buscarPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado."));
    }

    public Plato actualizar(Long id, Plato actualizado) {
        Plato p = buscarPorId(id);
        p.setNombre(actualizado.getNombre());
        p.setPrecio(actualizado.getPrecio());
        return repo.save(p);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Plato no encontrado.");
        }
        repo.deleteById(id);
    }

    public List<Plato> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }
}
