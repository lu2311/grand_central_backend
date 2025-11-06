package com.grandcentral.restaurant_backend.controller;

import com.grandcentral.restaurant_backend.model.Reserva;
import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reserva> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Reserva obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> listarPorUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return service.listarPorUsuario(usuario);
    }

    @PostMapping
    public ResponseEntity<Reserva> crear(@Validated @RequestBody Reserva reserva, Principal principal) {
        String correoUsuario = principal.getName();
        Reserva guardada = service.crear(reserva, correoUsuario);
        return ResponseEntity.created(URI.create("/api/reservas/" + guardada.getId())).body(guardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, Principal principal) {
        String correoUsuario = principal.getName();
        service.eliminar(id, correoUsuario);
        return ResponseEntity.noContent().build();
    }
}

