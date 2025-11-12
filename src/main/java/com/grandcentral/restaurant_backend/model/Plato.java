package com.grandcentral.restaurant_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Base64;

@Entity
@Table(name = "platos")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del plato es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @Min(value = 1, message = "El precio debe ser mayor que 0")
    @Column(nullable = false)
    private Double precio;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @Transient // No se guarda en la base de datos
    private String imagenBase64;

    public Plato() {
    }

    public Plato(String nombre, Double precio, byte[] imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;

        if (imagen != null) {
            this.imagenBase64 = Base64.getEncoder().encodeToString(imagen);
        }
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
        if (imagen != null) {
            this.imagenBase64 = Base64.getEncoder().encodeToString(imagen);
        }
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }
}
