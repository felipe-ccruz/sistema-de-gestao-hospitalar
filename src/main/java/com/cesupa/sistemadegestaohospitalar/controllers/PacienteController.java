package com.cesupa.sistemadegestaohospitalar.controllers;

import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    public Paciente cadastrarNovoPaciente(Paciente paciente) {
        try {
            return pacienteService.cadastrarPaciente(paciente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar paciente: " + e.getMessage());
        }
    }

    public List<Paciente> consultarPacientes(String termo) {
        return pacienteService.buscarPacientes(termo);
    }

    public Optional<Paciente> consultarPacientePorId(UUID id) {
        return pacienteService.buscarPorId(id);
    }

    public Paciente atualizarDadosPaciente(Paciente paciente) {
        try {
            return pacienteService.atualizarPaciente(paciente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar paciente: " + e.getMessage());
        }
    }

    public void inativarCadastroPaciente(UUID pacienteId, String motivo) {
        try {
            pacienteService.inativarPaciente(pacienteId, motivo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inativar paciente: " + e.getMessage());
        }
    }
}
