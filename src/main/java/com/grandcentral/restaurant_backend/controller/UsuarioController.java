package com.grandcentral.restaurant_backend.controller;

import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService service, PasswordEncoder passwordEncoder, UsuarioService usuarioService) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
    }

    // REGISTRO PÚBLICO (no requiere token JWT)
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@Validated @RequestBody Usuario usuario) {
        // ❌ elimina esta línea:
        // usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));

        Usuario guardado = service.crear(usuario);
        return ResponseEntity.created(URI.create("/api/usuarios/" + guardado.getId())).body(guardado);
    }

    // LISTAR TODOS (solo para admin o autenticados)
    @GetMapping
    public List<Usuario> listarTodos() {
        return service.listarTodos();
    }

    // OBTENER USUARIO POR ID (solo autenticados)
    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // ACTUALIZAR USUARIO (solo autenticados)
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @Validated @RequestBody Usuario usuario) {
        // Re-codifica la contraseña solo si viene en la solicitud
        if (usuario.getContrasenia() != null && !usuario.getContrasenia().isEmpty()) {
            usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        }
        return service.actualizar(id, usuario);
    }

    // ELIMINAR USUARIO (solo autenticados o admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerPerfil(Principal principal) {
        String correo = principal.getName();
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        return ResponseEntity.ok(usuario);
    }
}
