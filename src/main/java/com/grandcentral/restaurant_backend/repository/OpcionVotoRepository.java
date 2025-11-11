package com.grandcentral.restaurant_backend.repository;

import com.grandcentral.restaurant_backend.model.OpcionVoto;
import com.grandcentral.restaurant_backend.model.VotacionDelDia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcionVotoRepository extends JpaRepository<OpcionVoto, Long> {
    List<OpcionVoto> findByVotacionDelDiaAndTipo(VotacionDelDia votacionDelDia, String tipo);
}
