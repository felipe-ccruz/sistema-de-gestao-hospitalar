package com.cesupa.sistemadegestaohospitalar.repositories;

import com.cesupa.sistemadegestaohospitalar.entities.Consulta;
import com.cesupa.sistemadegestaohospitalar.entities.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    @Query("SELECT c FROM Consulta c WHERE c.paciente.id = :pacienteId AND c.status = 'AGENDADA'")
    List<Consulta> findConsultasAgendadasByPaciente(@Param("pacienteId") UUID pacienteId);

    @Query("SELECT COUNT(c) > 0 FROM Consulta c WHERE c.medico = :medico AND c.dataConsulta = :dataConsulta AND c.status = 'AGENDADA'")
    boolean existsConsultaAgendada(@Param("medico") Medico medico, @Param("dataConsulta") LocalDateTime dataConsulta);
}
