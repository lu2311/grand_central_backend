package com.grandcentral.restaurant_backend.controller;


import com.grandcentral.restaurant_backend.model.Plato;
import com.grandcentral.restaurant_backend.service.PlatoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService service;

    public PlatoController(PlatoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Plato> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Plato obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<Plato> buscarPorNombre(@RequestParam String nombre) {
        return service.buscarPorNombre(nombre);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Plato> crear(@Valid @RequestBody Plato plato) {
        Plato guardado = service.crear(plato);
        return ResponseEntity.created(URI.create("/api/platos/" + guardado.getId())).body(guardado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Plato actualizar(@PathVariable Long id, @Validated @RequestBody Plato plato) {
        return service.actualizar(id, plato);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
