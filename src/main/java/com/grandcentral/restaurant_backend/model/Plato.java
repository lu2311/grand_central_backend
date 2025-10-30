package com.GrandCentral.restaurant_backend.model;


import jakarta.persistence.*;

@Entity
@Table(name = "platos")
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    public Plato() {}

    public Plato(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }
}
