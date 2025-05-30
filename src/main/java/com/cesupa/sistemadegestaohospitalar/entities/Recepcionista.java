package com.cesupa.sistemadegestaohospitalar.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "recepcionista")
public class Recepcionista {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome_e_sobrenome", nullable = false)
    private String nomeESobrenome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String sexo;

    private String telefone;
    private String email;

    @Column(name = "tipo_sanguineo")
    private String tipoSanguineo;

    @Column(nullable = false)
    private String status = "ATIVO";

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false)
    private String turno;

    // Constructors
    public Recepcionista() {}

    public Recepcionista(String nomeESobrenome, String cpf, LocalDate dataNascimento,
                         String sexo, String turno, Usuario usuario) {
        this.nomeESobrenome = nomeESobrenome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.turno = turno;
        this.usuario = usuario;
        this.status = "ATIVO";
    }

    // Getters and Setters (similar pattern as above)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNomeESobrenome() { return nomeESobrenome; }
    public void setNomeESobrenome(String nomeESobrenome) { this.nomeESobrenome = nomeESobrenome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoSanguineo() { return tipoSanguineo; }
    public void setTipoSanguineo(String tipoSanguineo) { this.tipoSanguineo = tipoSanguineo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
}

