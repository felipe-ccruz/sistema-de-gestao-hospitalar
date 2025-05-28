package com.cesupa.sistemadegestaohospitalar.entities;

import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.entities.funcionario.Medico;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Consulta {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private LocalDateTime dataMarcacao;

    @Column(nullable = false)
    private LocalDateTime dataConsulta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status;

    // Construtor padrão
    public Consulta() {
    }

    // Construtor completo
    public Consulta(UUID id, Paciente paciente, Medico medico, LocalDateTime dataMarcacao, LocalDateTime dataConsulta, StatusConsulta status) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataMarcacao = dataMarcacao;
        this.dataConsulta = dataConsulta;
        this.status = status;
    }

    // Getters e Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataMarcacao() {
        return dataMarcacao;
    }

    public void setDataMarcacao(LocalDateTime dataMarcacao) {
        this.dataMarcacao = dataMarcacao;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    // Enum para status da consulta
    public enum StatusConsulta {
        AGENDADA,
        CANCELADA,
        REALIZADA
    }
}
