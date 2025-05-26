package com.cesupa.sistemadegestaohospitalar.entities.funcionario;

import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import com.cesupa.sistemadegestaohospitalar.entities.enums.TipoSanguineo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Funcionario {

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

    private String email;

    @Enumerated(EnumType.STRING)
    private TipoSanguineo tipoSanguineo;

    @Enumerated(EnumType.STRING)
    private StatusFuncionario status;

    @Column(nullable = false, unique = true)
    private String nomeUsuario;

    @Column(nullable = false)
    private String senhaUsuario;


    public Funcionario(String nome, String cpf, LocalDate dataNascimento, Sexo sexo) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
    }

    //    ENUMS
    public enum StatusFuncionario {
        ATIVO,
        INATIVO,
        AFASTADO
    }



}