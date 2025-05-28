package com.cesupa.sistemadegestaohospitalar.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha; // De preferência criptografada

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilUsuario perfilUsuario;

    public enum PerfilUsuario {
        ADMIN,
        RECEPCIONISTA,
        MEDICO
    }

    // Construtor padrão
    public Usuario() {
    }

    // Construtor completo
    public Usuario(UUID id, String login, String senha, PerfilUsuario perfilUsuario) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.perfilUsuario = perfilUsuario;
    }

    // Getters e Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilUsuario getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }
}
