package com.cesupa.sistemadegestaohospitalar.entities;

import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import com.cesupa.sistemadegestaohospitalar.entities.enums.TipoSanguineo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    // ENUMS
    public enum Status {
        ATIVO,
        INATIVO
    }
}
