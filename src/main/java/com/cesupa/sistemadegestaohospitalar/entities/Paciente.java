package com.cesupa.sistemadegestaohospitalar.entities;

import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import com.cesupa.sistemadegestaohospitalar.entities.enums.TipoSanguineo;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Paciente {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private String telefone;

    private String endereco;

    private String email;

    @Enumerated(EnumType.STRING)
    private TipoSanguineo tipoSanguineo;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Construtor padrão
    public Paciente() {
    }

    // Construtor completo
    public Paciente(UUID id, String nome, String cpf, LocalDate dataNascimento, Sexo sexo,
                    String telefone, String endereco, String email, TipoSanguineo tipoSanguineo,
                    Status status) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.tipoSanguineo = tipoSanguineo;
        this.status = status;
    }

    // Getters e setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // ENUMS
    public enum Status {
        ATIVO,
        INATIVO
    }

    public enum JustificativaInatividade {
        FALTA_DE_CONTATO,
        TRANSFERIDO,
        FALECIDO,
        OUTROS
    }
}
