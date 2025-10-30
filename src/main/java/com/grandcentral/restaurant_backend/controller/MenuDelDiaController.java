package com.GrandCentral.restaurant_backend.controller;

import com.GrandCentral.restaurant_backend.model.MenuDelDia;
import com.GrandCentral.restaurant_backend.service.MenuDelDiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
public class MenuDelDiaController {

    private final MenuDelDiaService service;

    public MenuDelDiaController(MenuDelDiaService service) {
        this.service = service;
    }

    @GetMapping
    public List<MenuDelDia> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public MenuDelDia obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/buscar-tipo")
    public List<MenuDelDia> buscarPorTipo(@RequestParam String tipo) {
        return service.buscarPorTipo(tipo);
    }

    @GetMapping("/buscar-fecha")
    public List<MenuDelDia> buscarPorFecha(@RequestParam String fecha) {
        return service.buscarPorFecha(LocalDate.parse(fecha));
    }

    @PostMapping
    public ResponseEntity<MenuDelDia> crear(@Validated @RequestBody MenuDelDia menu) {
        MenuDelDia guardado = service.crear(menu);
        return ResponseEntity.created(URI.create("/api/menus/" + guardado.getId())).body(guardado);
    }

    @PutMapping("/{id}")
    public MenuDelDia actualizar(@PathVariable Long id, @Validated @RequestBody MenuDelDia menu) {
        return service.actualizar(id, menu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
