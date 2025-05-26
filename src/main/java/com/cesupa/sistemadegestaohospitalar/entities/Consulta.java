package com.cesupa.sistemadegestaohospitalar.entities;

import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.entities.funcionario.Medico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private Medico medico;

    @Column(nullable = false)
    private LocalDateTime dataMarcacao;

    @Column(nullable = false)
    private LocalDateTime dataConsulta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status;

    public enum StatusConsulta {
        AGENDADA,
        CANCELADA,
        REALIZADA
    }
}