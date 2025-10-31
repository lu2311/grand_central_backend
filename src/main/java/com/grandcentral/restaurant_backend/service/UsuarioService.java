package com.grandcentral.restaurant_backend.service;

import com.grandcentral.restaurant_backend.model.Usuario;
import com.grandcentral.restaurant_backend.repository.UsuarioRepository;
import com.grandcentral.restaurant_backend.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UsuarioService(
            UsuarioRepository repo,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // 🔹 Registro de usuario
    public Usuario crear(Usuario usuario) {
        if (repo.existsByCorreo(usuario.getCorreo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo ya está registrado.");
        }

        // Encriptar contraseña antes de guardar
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));

        return repo.save(usuario);
    }

    // 🔹 Login (autenticación y generación de token JWT)
    public String login(String correo, String contrasenia) {
        try {
            // Verificar credenciales
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(correo, contrasenia)
            );

            // Buscar usuario
            Usuario usuario = repo.findByCorreo(correo)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

            // Generar token JWT
            return jwtService.generateToken(usuario);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas.");
        }
    }

    // 🔹 Listar todos los usuarios
    public List<Usuario> listarTodos() {
        return repo.findAll();
    }

    // 🔹 Buscar usuario por ID
    public Usuario buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));
    }

    // 🔹 Actualizar datos del usuario
    public Usuario actualizar(Long id, Usuario actualizado) {
        Usuario u = buscarPorId(id);
        u.setNombre(actualizado.getNombre());
        u.setApellido(actualizado.getApellido());
        u.setCorreo(actualizado.getCorreo());

        // Si viene una nueva contraseña, se vuelve a encriptar
        if (actualizado.getContrasenia() != null && !actualizado.getContrasenia().isEmpty()) {
            u.setContrasenia(passwordEncoder.encode(actualizado.getContrasenia()));
        }

        u.setRol(actualizado.getRol());
        return repo.save(u);
    }

    // 🔹 Eliminar usuario
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado.");
        }
        repo.deleteById(id);
    }
}
