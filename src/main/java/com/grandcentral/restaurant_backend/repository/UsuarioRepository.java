package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.Usuario;    
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por correo (útil para login)
    Optional<Usuario> findByCorreo(String correo);

    // Verificar si ya existe un usuario con ese correo
    boolean existsByCorreo(String correo);
    
}
