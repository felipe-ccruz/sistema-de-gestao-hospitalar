package com.cesupa.sistemadegestaohospitalar.repositories;

import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.entities.Paciente.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

    // Buscar pacientes por status (ATIVO ou INATIVO)
    List<Paciente> findByStatus(Status status);

    // Buscar paciente por CPF
    Paciente findByCpf(String cpf);
}
