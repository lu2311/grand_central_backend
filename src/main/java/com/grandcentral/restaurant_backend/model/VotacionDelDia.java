package com.grandcentral.restaurant_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "votaciones_dia")
public class VotacionDelDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate fecha = LocalDate.now();

    @Column(nullable = false)
    private String estado = "ABIERTA"; // ABIERTA | CERRADA

    @OneToMany(mappedBy = "votacionDelDia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OpcionVoto> opciones;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<OpcionVoto> getOpciones() { return opciones; }
    public void setOpciones(List<OpcionVoto> opciones) { this.opciones = opciones; }
}