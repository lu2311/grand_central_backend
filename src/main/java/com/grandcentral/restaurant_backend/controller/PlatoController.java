package com.grandcentral.restaurant_backend.controller;


import com.grandcentral.restaurant_backend.model.Plato;
import com.grandcentral.restaurant_backend.service.PlatoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/platos")
public class PlatoController {

    private final PlatoService service;

    public PlatoController(PlatoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Plato> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Plato obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<Plato> buscarPorNombre(@RequestParam String nombre) {
        return service.buscarPorNombre(nombre);
    }

    //@PreAuthorize("hasRole('ADMIN')")
@PostMapping(consumes = {"multipart/form-data"})
public ResponseEntity<Plato> crear(
        @RequestPart("nombre") String nombre,
        @RequestPart("precio") Double precio,
        @RequestPart(value = "imagen", required = false) MultipartFile imagenFile
) throws IOException {

    String imagenPath = null;
    if (imagenFile != null && !imagenFile.isEmpty()) {
        String fileName = System.currentTimeMillis() + "_" + imagenFile.getOriginalFilename();
        String uploadDir = "uploads/";
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) uploadFolder.mkdirs();
        imagenFile.transferTo(new File(uploadDir + fileName));
        imagenPath = "/uploads/" + fileName;
    }

    Plato guardado = service.crear(new Plato(nombre, precio, imagenPath));
    return ResponseEntity.created(URI.create("/api/platos/" + guardado.getId())).body(guardado);
}


    //@PreAuthorize("hasRole('ADMIN')")
@PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
public Plato actualizar(
        @PathVariable Long id,
        @RequestPart("nombre") String nombre,
        @RequestPart("precio") Double precio,
        @RequestPart(value = "imagen", required = false) MultipartFile imagenFile
) throws IOException {

    Plato existente = service.buscarPorId(id);
    String imagenPath = existente.getImagen();

    if (imagenFile != null && !imagenFile.isEmpty()) {
        String fileName = System.currentTimeMillis() + "_" + imagenFile.getOriginalFilename();
        String uploadDir = "uploads/";
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) uploadFolder.mkdirs();
        imagenFile.transferTo(new File(uploadDir + fileName));
        imagenPath = "/uploads/" + fileName;
    }

    existente.setNombre(nombre);
    existente.setPrecio(precio);
    existente.setImagen(imagenPath);

    return service.crear(existente);
}

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
