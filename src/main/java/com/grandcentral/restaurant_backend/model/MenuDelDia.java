package com.GrandCentral.restaurant_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "menu_del_dia")
public class MenuDelDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo; // entrada o fondo

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private Double precio;

    public MenuDelDia() {}

    public MenuDelDia(String nombre, String tipo, LocalDate fecha, Double precio) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.fecha = fecha;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }
}
