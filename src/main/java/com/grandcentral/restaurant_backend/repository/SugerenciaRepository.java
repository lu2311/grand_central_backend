package com.GrandCentral.restaurant_backend.repository;

import com.GrandCentral.restaurant_backend.model.Sugerencia;
import com.GrandCentral.restaurant_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SugerenciaRepository extends JpaRepository<Sugerencia, Long> {

    // Buscar todas las sugerencias de un usuario específico
    List<Sugerencia> findByUsuario(Usuario usuario);
}
