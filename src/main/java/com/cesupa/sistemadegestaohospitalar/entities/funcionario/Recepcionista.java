package com.cesupa.sistemadegestaohospitalar.entities.funcionario;

import com.cesupa.sistemadegestaohospitalar.entities.Usuario;
import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Entity
public class Recepcionista extends Funcionario {

    @Enumerated(EnumType.STRING)
    private Turno turno;

    // Construtor padrão
    public Recepcionista() {
        super();
    }

    public Recepcionista(String nome, String cpf, LocalDate dataNascimento, Sexo sexo, Usuario usuario, Turno turno) {
        super(nome, cpf, dataNascimento, sexo, usuario); // Agora recebe um objeto Usuario
        this.turno = turno;
    }


    // Getter para turno
    public Turno getTurno() {
        return turno;
    }

    // Setter para turno
    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    // Enum Turno
    public enum Turno {
        MANHA,
        TARDE,
        NOITE,
        MADRUGADA
    }
}
