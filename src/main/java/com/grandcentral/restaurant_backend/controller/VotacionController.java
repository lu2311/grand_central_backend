package com.GrandCentral.restaurant_backend.controller;

import com.GrandCentral.restaurant_backend.model.Votacion;
import com.GrandCentral.restaurant_backend.service.VotacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/votaciones")
public class VotacionController {

    private final VotacionService service;

    public VotacionController(VotacionService service) {
        this.service = service;
    }

    // POST: votar
    @PostMapping
    public ResponseEntity<Votacion> votar(@RequestParam Long usuarioId, @RequestParam Long menuId) {
        Votacion guardada = service.votar(usuarioId, menuId);
        return ResponseEntity.ok(guardada);
    }

    // GET: listar todas las votaciones
    @GetMapping
    public List<Votacion> listarTodas() {
        return service.listarTodas();
    }

    // GET: contar votos de un menú
    @GetMapping("/conteo/{menuId}")
    public ResponseEntity<Long> contarVotos(@PathVariable Long menuId) {
        long total = service.contarVotosPorMenu(menuId);
        return ResponseEntity.ok(total);
    }
}
