package com.cesupa.sistemadegestaohospitalar.entities.funcionario;

import com.cesupa.sistemadegestaohospitalar.entities.Usuario;
import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Medico extends Funcionario {

    @Column(nullable = false, unique = true)
    private String crm;

    @Column(nullable = false)
    private String especialidade;

    // Construtor vazio
    public Medico() {
        super();
    }

    // Construtor com campos da superclasse e os próprios
    public Medico(String nome, String cpf, LocalDate dataNascimento, Sexo sexo, Usuario usuario, String crm, String especialidade) {
        super(nome, cpf, dataNascimento, sexo, usuario); // Agora recebe um objeto Usuario
        this.crm = crm;
        this.especialidade = especialidade;
    }

    // Construtor completo (caso necessário) pode ser adicionado também

    // Getters e Setters
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
