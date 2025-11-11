package com.grandcentral.restaurant_backend.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.grandcentral.restaurant_backend.model.MenuDelDia;
import com.grandcentral.restaurant_backend.model.OpcionVoto;
import com.grandcentral.restaurant_backend.model.VotacionDelDia;
import com.grandcentral.restaurant_backend.repository.VotacionDelDiaRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class MenuGeneratorScheduler {

    private final VotacionDelDiaRepository votacionRepo;
    private final MenuDelDiaService menuService;

    public MenuGeneratorScheduler(VotacionDelDiaRepository votacionRepo, MenuDelDiaService menuService) {
        this.votacionRepo = votacionRepo;
        this.menuService = menuService;
    }

    // Ejecuta todos los días a las 00:00 (hora de Lima)
    @Transactional
    @Scheduled(cron = "0 0 8 * * *", zone = "America/Lima")
    public void generarMenuDiario() {
        LocalDate hoy = LocalDate.now(java.time.ZoneId.of("America/Lima"));
        LocalDate ayer = hoy.minusDays(1);

        Optional<VotacionDelDia> maybe = votacionRepo.findByFecha(ayer);
        if (maybe.isEmpty()) return;

        VotacionDelDia votacion = maybe.get();
        if ("CERRADA".equalsIgnoreCase(votacion.getEstado())) return;

        // Top 2 entradas
        List<String> entradasTop = votacion.getOpciones().stream()
                .filter(o -> "ENTRADA".equalsIgnoreCase(o.getTipo()))
                .sorted(Comparator.comparingInt(OpcionVoto::getVotos).reversed())
                .limit(2)
                .map(OpcionVoto::getNombre)
                .collect(Collectors.toList());

        // Top 2 fondos
        List<String> fondosTop = votacion.getOpciones().stream()
                .filter(o -> "FONDO".equalsIgnoreCase(o.getTipo()))
                .sorted(Comparator.comparingInt(OpcionVoto::getVotos).reversed())
                .limit(2)
                .map(OpcionVoto::getNombre)
                .collect(Collectors.toList());

        // Si faltan candidatos (ej. menos de 2), decide: abortar o rellenar con placeholders
        if (entradasTop.size() < 2 || fondosTop.size() < 2) {
            // podrías lanzar excepción o simplemente no generar
            return;
        }

        MenuDelDia menu = new MenuDelDia(hoy, entradasTop, fondosTop, 12.0);
        menu.setGeneradoAutomaticamente(true);
        menuService.guardarDirecto(menu);

        // marcar votación como cerrada
        votacion.setEstado("CERRADA");
        // guarda la votación (necesitas el repo)
        votacionRepo.save(votacion);
    }
}