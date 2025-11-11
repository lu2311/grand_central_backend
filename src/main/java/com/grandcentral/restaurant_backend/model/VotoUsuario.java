package com.grandcentral.restaurant_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "votos_usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "fecha_voto"})
})
public class VotoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("votos")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "entrada_id")
    private OpcionVoto entrada;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fondo_id")
    private OpcionVoto fondo;

    @Column(name = "fecha_voto", nullable = false)
    private LocalDate fechaVoto = LocalDate.now();

    public VotoUsuario() {}

    public VotoUsuario(Usuario usuario, OpcionVoto entrada, OpcionVoto fondo, LocalDate fechaVoto) {
        this.usuario = usuario;
        this.entrada = entrada;
        this.fondo = fondo;
        this.fechaVoto = fechaVoto;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public OpcionVoto getEntrada() { return entrada; }
    public void setEntrada(OpcionVoto entrada) { this.entrada = entrada; }

    public OpcionVoto getFondo() { return fondo; }
    public void setFondo(OpcionVoto fondo) { this.fondo = fondo; }

    public LocalDate getFechaVoto() { return fechaVoto; }
    public void setFechaVoto(LocalDate fechaVoto) { this.fechaVoto = fechaVoto; }
}
