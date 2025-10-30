package com.GrandCentral.restaurant_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "votaciones", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "fecha_voto"})
})
public class Votacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_id")
    private MenuDelDia menu;

    @Column(name = "fecha_voto", nullable = false)
    private LocalDate fechaVoto = LocalDate.now();

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public MenuDelDia getMenu() { return menu; }
    public void setMenu(MenuDelDia menu) { this.menu = menu; }

    public LocalDate getFechaVoto() { return fechaVoto; }
    public void setFechaVoto(LocalDate fechaVoto) { this.fechaVoto = fechaVoto; }
}
