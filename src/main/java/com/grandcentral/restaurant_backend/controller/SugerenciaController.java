package com.grandcentral.restaurant_backend.controller;

import com.grandcentral.restaurant_backend.model.Sugerencia;
import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.service.SugerenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/sugerencias")
public class SugerenciaController {

    private final SugerenciaService service;

    public SugerenciaController(SugerenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sugerencia> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public Sugerencia obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    
    @GetMapping("/usuario/{usuarioId}")
    public List<Sugerencia> listarPorUsuario(@PathVariable Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return service.listarPorUsuario(usuario);
    }

    @PostMapping
    public ResponseEntity<Sugerencia> crear(@Validated @RequestBody Sugerencia sugerencia, Principal principal) {
        String correoUsuario = principal.getName(); // obtiene el correo del JWT o sesión
        Sugerencia guardada = service.crear(sugerencia, correoUsuario);
        return ResponseEntity.created(URI.create("/api/sugerencias/" + guardada.getId())).body(guardada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
