package com.cesupa.sistemadegestaohospitalar.entities.funcionario;

import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medico extends Funcionario {

    @Column(nullable = false, unique = true)
    private String crm;

    @Column(nullable = false)
    private String especialidade;


    public Medico(String nome, String cpf, LocalDate dataNascimento, Sexo sexo, String crm, String especialidade) {
        super(nome, cpf, dataNascimento, sexo);
        this.crm = crm;
        this.especialidade = especialidade;
    }
}
