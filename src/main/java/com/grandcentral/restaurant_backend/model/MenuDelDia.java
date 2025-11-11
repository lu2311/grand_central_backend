package com.grandcentral.restaurant_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "menu_del_dia", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fecha"})
})
public class MenuDelDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @ElementCollection
    @CollectionTable(name = "menu_entradas", joinColumns = @JoinColumn(name = "menu_id"))
    @Column(name = "entrada")
    private List<String> entradas;

    @ElementCollection
    @CollectionTable(name = "menu_fondos", joinColumns = @JoinColumn(name = "menu_id"))
    @Column(name = "fondo")
    private List<String> fondos;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private boolean generadoAutomaticamente = true; // opcional: si el sistema lo generó

    public MenuDelDia() {}

    public MenuDelDia(LocalDate fecha, List<String> entradas, List<String> fondos, Double precio) {
        this.fecha = fecha;
        this.entradas = entradas;
        this.fondos = fondos;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public List<String> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<String> entradas) {
        this.entradas = entradas;
    }

    public List<String> getFondos() {
        return fondos;
    }

    public void setFondos(List<String> fondos) {
        this.fondos = fondos;
    }

    public Double getPrecio() { return precio; }

    public void setPrecio(Double precio) { this.precio = precio; }

    public void setGeneradoAutomaticamente(boolean generadoAutomaticamente) {
        this.generadoAutomaticamente = generadoAutomaticamente;
    }

    public boolean isGeneradoAutomaticamente() {
        return generadoAutomaticamente;
    }
    
}
