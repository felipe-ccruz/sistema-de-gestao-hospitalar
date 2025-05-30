package com.cesupa.sistemadegestaohospitalar.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesupa.sistemadegestaohospitalar.entities.Consulta;
import com.cesupa.sistemadegestaohospitalar.entities.Medico;
import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.repositories.ConsultaRepository;
import com.cesupa.sistemadegestaohospitalar.repositories.MedicoRepository;
import com.cesupa.sistemadegestaohospitalar.repositories.PacienteRepository;

@Service
@Transactional
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public Consulta agendarConsulta(UUID pacienteId, UUID medicoId, LocalDateTime dataConsulta) {
        // Validar data futura
        if (dataConsulta.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Não é possível agendar consultas para datas passadas");
        }

        // Buscar paciente
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(pacienteId);
        if (pacienteOpt.isEmpty() || !"ATIVO".equals(pacienteOpt.get().getStatus())) {
            throw new IllegalArgumentException("Paciente não encontrado ou inativo");
        }

        // Buscar médico
        Optional<Medico> medicoOpt = medicoRepository.findById(medicoId);
        if (medicoOpt.isEmpty() || !"ATIVO".equals(medicoOpt.get().getStatus())) {
            throw new IllegalArgumentException("Médico não encontrado ou inativo");
        }

        Medico medico = medicoOpt.get();

        // Verificar disponibilidade do horário
        if (consultaRepository.existsConsultaAgendada(medico, dataConsulta)) {
            throw new IllegalArgumentException("Horário não disponível para este médico");
        }

        // Criar e salvar consulta
        Consulta consulta = new Consulta(pacienteOpt.get(), medico, dataConsulta);
        return consultaRepository.save(consulta);
    }
}
