package com.GrandCentral.restaurant_backend.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasenia;

    @Column(nullable = false)
    private String rol = "usuario"; // valores posibles: usuario, admin

    // Relaciones
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sugerencia> sugerencias = new ArrayList<>();

    // Constructor vacío (requerido por JPA)
    public Usuario() {}

    // Constructor completo
    public Usuario(String nombre, String apellido, String correo, String contrasenia, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }

    // ==============================
    // 🔹 Métodos para Spring Security
    // ==============================

    /**
     * Devuelve los roles del usuario en formato GrantedAuthority.
     * Spring Security requiere que los roles empiecen con "ROLE_".
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + rol.toUpperCase());
    }

    /** Devuelve la contraseña para la autenticación. */
    @Override
    public String getPassword() {
        return contrasenia;
    }

    /** Devuelve el nombre de usuario usado para el login (correo en este caso). */
    @Override
    public String getUsername() {
        return correo;
    }

    // Estas propiedades pueden quedarse true si no usas bloqueo/expiración
    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    // ==============================
    // 🔹 Getters y Setters normales
    // ==============================

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }

    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasenia() { return contrasenia; }

    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    public String getRol() { return rol; }

    public void setRol(String rol) { this.rol = rol; }

    public List<Reserva> getReservas() { return reservas; }

    public List<Sugerencia> getSugerencias() { return sugerencias; }
}
