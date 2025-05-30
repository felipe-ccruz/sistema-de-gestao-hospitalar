package com.cesupa.sistemadegestaohospitalar.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.repositories.ConsultaRepository;
import com.cesupa.sistemadegestaohospitalar.repositories.PacienteRepository;

@Service
@Transactional
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public Paciente cadastrarPaciente(Paciente paciente) {
        if (pacienteRepository.existsByCpf(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema");
        }

        if (!isValidCPF(paciente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> buscarPacientes(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return pacienteRepository.findAllAtivos();
        }

        // Tenta buscar por CPF primeiro
        Optional<Paciente> pacientePorCpf = pacienteRepository.findByCpf(termo);
        if (pacientePorCpf.isPresent() && "ATIVO".equals(pacientePorCpf.get().getStatus())) {
            return List.of(pacientePorCpf.get());
        }

        // Busca por nome
        return pacienteRepository.findByNomeContainingIgnoreCaseAndStatusAtivo(termo);
    }

    public Optional<Paciente> buscarPorId(UUID id) {
        return pacienteRepository.findById(id);
    }

    public Paciente atualizarPaciente(Paciente paciente) {
        if (!pacienteRepository.existsById(paciente.getId())) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        // Validar se não há outro paciente com o mesmo CPF
        Optional<Paciente> existente = pacienteRepository.findByCpf(paciente.getCpf());
        if (existente.isPresent() && !existente.get().getId().equals(paciente.getId())) {
            throw new IllegalArgumentException("CPF já cadastrado para outro paciente");
        }

        return pacienteRepository.save(paciente);
    }

    public void inativarPaciente(UUID pacienteId, String motivo) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(pacienteId);
        if (pacienteOpt.isEmpty()) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        // Verificar se há consultas agendadas
        if (!consultaRepository.findConsultasAgendadasByPaciente(pacienteId).isEmpty()) {
            throw new IllegalArgumentException("Paciente possui consultas agendadas. Cancele-as antes de inativar.");
        }

        Paciente paciente = pacienteOpt.get();
        paciente.setStatus("INATIVO");
        paciente.setJustificaStatus(motivo);
        pacienteRepository.save(paciente);
    }

    private boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) return false;

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("\\D", "");

        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) return false;

        // Calcula primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = soma % 11;
        int dv1 = resto < 2 ? 0 : 11 - resto;

        // Calcula segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = soma % 11;
        int dv2 = resto < 2 ? 0 : 11 - resto;

        return Character.getNumericValue(cpf.charAt(9)) == dv1 &&
                Character.getNumericValue(cpf.charAt(10)) == dv2;
    }
}
