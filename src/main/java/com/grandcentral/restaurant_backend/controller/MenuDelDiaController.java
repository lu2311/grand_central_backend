package com.grandcentral.restaurant_backend.controller;

import com.grandcentral.restaurant_backend.model.MenuDelDia;
import com.grandcentral.restaurant_backend.service.MenuDelDiaService;
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

    // Listar todos los menús creados
    @GetMapping
    public List<MenuDelDia> listarTodos() {
        return service.listarTodos();
    }

    // Obtener el menú de una fecha específica
    @GetMapping("/fecha/{fecha}")
    public MenuDelDia obtenerPorFecha(@PathVariable String fecha) {
        LocalDate fechaParseada = LocalDate.parse(fecha);
        return service.buscarPorFecha(fechaParseada);
    }

    // Crear manualmente un menú (solo si no existe uno para esa fecha)
    @PostMapping
    public ResponseEntity<MenuDelDia> crear(@Validated @RequestBody MenuDelDia menu) {
        MenuDelDia guardado = service.crear(menu);
        return ResponseEntity.created(URI.create("/api/menus/fecha/" + guardado.getFecha())).body(guardado);
    }

    // Eliminar un menú por fecha
    @DeleteMapping("/fecha/{fecha}")
    public ResponseEntity<Void> eliminarPorFecha(@PathVariable String fecha) {
        LocalDate fechaParseada = LocalDate.parse(fecha);
        service.eliminarPorFecha(fechaParseada);
        return ResponseEntity.noContent().build();
    }
}