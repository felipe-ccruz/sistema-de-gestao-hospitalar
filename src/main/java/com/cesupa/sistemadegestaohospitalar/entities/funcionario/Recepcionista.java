package com.cesupa.sistemadegestaohospitalar.entities.funcionario;

import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recepcionista extends Funcionario {

    @Enumerated(EnumType.STRING)
    private Turno turno;

    public Recepcionista(String nome, String cpf, LocalDate dataNascimento, Sexo sexo, Turno turno) {
        super(nome, cpf, dataNascimento, sexo);
        this.turno = turno;
    }

    public enum Turno {
        MANHA,
        TARDE,
        NOITE,
        MADRUGADA
    }

}
