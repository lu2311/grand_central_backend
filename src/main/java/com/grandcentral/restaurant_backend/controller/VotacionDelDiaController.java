package com.grandcentral.restaurant_backend.controller;

import com.grandcentral.restaurant_backend.model.*;
import com.grandcentral.restaurant_backend.repository.UsuarioRepository;
import com.grandcentral.restaurant_backend.security.JwtService;
import com.grandcentral.restaurant_backend.service.VotacionDelDiaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/votaciones")
public class VotacionDelDiaController {

    private final VotacionDelDiaService service;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public VotacionDelDiaController(VotacionDelDiaService service, JwtService jwtService,
            UsuarioRepository usuarioRepository) {
        this.service = service;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<VotacionDelDia>> listarTodas() {
        return ResponseEntity.ok(service.listarTodasLasVotaciones());
    }

    // Crear votación (solo admin)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VotacionDelDia> crear(@RequestBody Map<String, List<String>> request) {
        List<String> entradas = request.get("entradas");
        List<String> fondos = request.get("fondos");
        return ResponseEntity.ok(service.crearVotacion(entradas, fondos));
    }

    // Listar opciones del día
    @GetMapping("/opciones")
    public ResponseEntity<List<OpcionVoto>> listarOpcionesHoy() {
        return ResponseEntity.ok(service.listarOpcionesHoy());
    }

    // Registrar voto (usuario logueado)
    @PostMapping("/votar")
    public ResponseEntity<VotoUsuario> votar(
            @RequestBody Map<String, Long> request,
            HttpServletRequest httpRequest) {

        // Extraer token del header
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String token = authHeader.substring(7);

        // Obtener email (sub) del token
        String emailUsuario = jwtService.extractUsername(token);

        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByCorreo(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener los IDs desde el JSON
        Long entradaId = request.get("entradaId");
        Long fondoId = request.get("fondoId");

        // Validación simple por si faltan datos
        if (entradaId == null || fondoId == null) {
            return ResponseEntity.badRequest().build();
        }

        // Registrar el voto
        VotoUsuario voto = service.votar(usuario.getId(), entradaId, fondoId);
        return ResponseEntity.ok(voto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Void> eliminarVotacion() {
        service.eliminarVotacionDelDia();
        return ResponseEntity.noContent().build();
    }
}