package com.GrandCentral.restaurant_backend.security;

import com.GrandCentral.restaurant_backend.model.Usuario;
import com.GrandCentral.restaurant_backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));

        // 🔹 Importante: devolver la contraseña encriptada
        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasenia())
                .roles(usuario.getRol()) // por ejemplo "ADMIN", "USER"
                .build();
    }
}
