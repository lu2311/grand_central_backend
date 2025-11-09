package com.grandcentral.restaurant_backend.service;

import com.grandcentral.restaurant_backend.model.*;
import com.grandcentral.restaurant_backend.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VotacionDelDiaService {

    private final VotacionDelDiaRepository votacionRepo;
    private final OpcionVotoRepository opcionRepo;
    private final VotoUsuarioRepository votoUsuarioRepo;
    private final UsuarioRepository usuarioRepo;

    public VotacionDelDiaService(VotacionDelDiaRepository votacionRepo,
            OpcionVotoRepository opcionRepo,
            VotoUsuarioRepository votoUsuarioRepo,
            UsuarioRepository usuarioRepo) {
        this.votacionRepo = votacionRepo;
        this.opcionRepo = opcionRepo;
        this.votoUsuarioRepo = votoUsuarioRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public List<VotacionDelDia> listarTodasLasVotaciones() {
        return votacionRepo.findAll();
    }

    public VotacionDelDia crearVotacion(List<String> entradas, List<String> fondos) {
        if (entradas.size() < 3 || fondos.size() < 3)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe haber al menos 3 entradas y 3 fondos");

        if (votacionRepo.findByFecha(LocalDate.now()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una votación para hoy");

        VotacionDelDia votacion = new VotacionDelDia();
        votacion.setFecha(LocalDate.now());
        votacion = votacionRepo.save(votacion);

        List<OpcionVoto> opciones = new ArrayList<>();

        for (String nombre : entradas) {
            OpcionVoto o = new OpcionVoto();
            o.setTipo("ENTRADA");
            o.setNombre(nombre);
            o.setVotacionDelDia(votacion);
            opciones.add(o);
        }

        for (String nombre : fondos) {
            OpcionVoto o = new OpcionVoto();
            o.setTipo("FONDO");
            o.setNombre(nombre);
            o.setVotacionDelDia(votacion);
            opciones.add(o);
        }

        opcionRepo.saveAll(opciones);
        votacion.setOpciones(opciones);
        return votacionRepo.save(votacion);
    }

    public VotoUsuario votar(Long usuarioId, Long entradaId, Long fondoId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        if (votoUsuarioRepo.findByUsuarioAndFechaVoto(usuario, LocalDate.now()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya votaste hoy");

        OpcionVoto entrada = opcionRepo.findById(entradaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrada no encontrada"));
        OpcionVoto fondo = opcionRepo.findById(fondoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fondo no encontrado"));

        entrada.setVotos(entrada.getVotos() + 1);
        fondo.setVotos(fondo.getVotos() + 1);
        opcionRepo.save(entrada);
        opcionRepo.save(fondo);

        VotoUsuario voto = new VotoUsuario();
        voto.setUsuario(usuario);
        voto.setEntrada(entrada);
        voto.setFondo(fondo);
        voto.setFechaVoto(LocalDate.now());
        return votoUsuarioRepo.save(voto);
    }

    public List<OpcionVoto> listarOpcionesHoy() {
        VotacionDelDia votacion = votacionRepo.findByFecha(LocalDate.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay votación activa hoy"));
        return votacion.getOpciones();
    }

    public void eliminarVotacionDelDia() {
        VotacionDelDia votacion = votacionRepo.findByFecha(LocalDate.now())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay votación activa hoy"));

        // Obtener los IDs de las opciones relacionadas
        List<Long> opcionIds = votacion.getOpciones()
                .stream()
                .map(OpcionVoto::getId)
                .toList();

        // Eliminar los votos asociados
        List<VotoUsuario> votosAsociados = votoUsuarioRepo.findByEntradaIdInOrFondoIdIn(opcionIds, opcionIds);
        votoUsuarioRepo.deleteAll(votosAsociados);

        // Eliminar las opciones y la votación
        opcionRepo.deleteAll(votacion.getOpciones());
        votacionRepo.delete(votacion);
    }
}
