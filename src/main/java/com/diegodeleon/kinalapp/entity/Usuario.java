package com.diegodeleon.kinalapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @Column(name = "codigo_usuario")
    private Long codigoUsuario;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String rol;

    private int estado;

    public Usuario() {}

    public Usuario(Long codigoUsuario, String username, String password, String email, String rol, int estado) {
        this.codigoUsuario = codigoUsuario;
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
    }

    //UserDetails - Spring Security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.rol));                 //devuelve al usuario una lista de roles, requiere que los roles tengan el prefijo _ROLE
    }

    @Override
    public boolean isAccountNonExpired() {                                                  //indica si la cuenta ha expirado (tiempo)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {                                                   //indica si la cuenta fue bloqueada, supongamos, demasiados intentos de inicio de sesión
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {                                             //indica si la contraseña ha expirado
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.estado == 1;                                                           //indica si la cuenta esta activa o no (1=activo, 0=inactivo)
    }

    public Long getCodigoUsuario() { return codigoUsuario; }

    public void setCodigoUsuario(Long codigoUsuario) { this.codigoUsuario = codigoUsuario; }

    @Override
    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    @Override
    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }

    public void setRol(String rol) { this.rol = rol; }

    public int getEstado() { return estado; }

    public void setEstado(int estado) { this.estado = estado; }
}