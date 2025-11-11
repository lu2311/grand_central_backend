package com.grandcentral.restaurant_backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne(optional = true)
    @JoinColumn(name = "plato_id")
    private Plato plato;

    @ManyToOne(optional = true)
    @JoinColumn(name = "menu_id")
    @JsonIgnoreProperties({"entradas", "fondos", "precio", "generadoAutomaticamente", "fecha"})
    private MenuDelDia menu;

    @Column(name = "entrada_elegida")
    private String entradaElegida;

    @Column(name = "fondo_elegido")
    private String fondoElegido;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Column(name = "hora_reserva", nullable = false)
    private LocalDateTime horaReserva = LocalDateTime.now();

    public Reserva() {
    }

    public Reserva(Usuario usuario, Plato plato, MenuDelDia menu, String entradaElegida, String fondoElegido, LocalDate fechaReserva) {
        this.usuario = usuario;
        this.plato = plato;
        this.menu = menu;
        this.entradaElegida = entradaElegida;
        this.fondoElegido = fondoElegido;
        this.fechaReserva = fechaReserva;
        this.horaReserva = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public MenuDelDia getMenu() {
        return menu;
    }

    public void setMenu(MenuDelDia menu) {
        this.menu = menu;
    }

    public String getEntradaElegida() {
        return entradaElegida;
    }

    public void setEntradaElegida(String entradaElegida) {
        this.entradaElegida = entradaElegida;
    }

    public String getFondoElegido() {
        return fondoElegido;
    }

    public void setFondoElegido(String fondoElegido) {
        this.fondoElegido = fondoElegido;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDateTime getHoraReserva() {
        return horaReserva;
    }

    public void setHoraReserva(LocalDateTime horaReserva) {
        this.horaReserva = horaReserva;
    }
}