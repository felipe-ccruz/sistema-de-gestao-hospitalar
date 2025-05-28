package com.cesupa.sistemadegestaohospitalar.repositories;

import com.cesupa.sistemadegestaohospitalar.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    // Buscar consultas por status
    List<Consulta> findByStatus(Consulta.StatusConsulta status);

    // Buscar consultas por paciente (por exemplo, pelo objeto Paciente)
    List<Consulta> findByPacienteId(UUID pacienteId);

    // Buscar consultas por médico (pelo objeto Medico)
    List<Consulta> findByMedicoId(UUID medicoId);
}
