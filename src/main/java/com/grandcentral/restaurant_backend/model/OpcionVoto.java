package com.grandcentral.restaurant_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "opciones_voto")
public class OpcionVoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // "ENTRADA" o "FONDO"

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private int votos = 0;

    @ManyToOne
    @JoinColumn(name = "votacion_dia_id")
    @JsonBackReference
    private VotacionDelDia votacionDelDia;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getVotos() { return votos; }
    public void setVotos(int votos) { this.votos = votos; }

    public VotacionDelDia getVotacionDelDia() { return votacionDelDia; }
    public void setVotacionDelDia(VotacionDelDia votacionDelDia) { this.votacionDelDia = votacionDelDia; }
}
