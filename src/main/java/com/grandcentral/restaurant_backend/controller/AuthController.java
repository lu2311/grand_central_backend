package com.grandcentral.restaurant_backend.controller;

import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.security.JwtService;
import com.grandcentral.restaurant_backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public AuthController(JwtService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = usuarioService.login(request.getCorreo(), request.getContrasenia());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Clases internas para el request y response
    public static class LoginRequest {
        private String correo;
        private String contrasenia;

        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }

        public String getContrasenia() { return contrasenia; }
        public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    }

    public static class JwtResponse {
        private String token;
        public JwtResponse(String token) { this.token = token; }
        public String getToken() { return token; }
    }
}
