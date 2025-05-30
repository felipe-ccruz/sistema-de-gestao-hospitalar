package com.cesupa.sistemadegestaohospitalar.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_usuario", nullable = false, unique = true)
    private String nomeUsuario;

    @Column(name = "perfil_usuario", nullable = false)
    private String perfilUsuario;

    @Column(nullable = false)
    private String senha;

    // Constructors
    public Usuario() {}

    public Usuario(String nomeUsuario, String perfilUsuario, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.perfilUsuario = perfilUsuario;
        this.senha = senha;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getPerfilUsuario() { return perfilUsuario; }
    public void setPerfilUsuario(String perfilUsuario) { this.perfilUsuario = perfilUsuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
