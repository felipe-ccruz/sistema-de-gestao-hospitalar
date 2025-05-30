package com.cesupa.sistemadegestaohospitalar.controllers;

import com.cesupa.sistemadegestaohospitalar.entities.Consulta;
import com.cesupa.sistemadegestaohospitalar.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    public Consulta agendarNovaConsulta(UUID pacienteId, UUID medicoId, LocalDateTime dataConsulta) {
        try {
            return consultaService.agendarConsulta(pacienteId, medicoId, dataConsulta);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao agendar consulta: " + e.getMessage());
        }
    }
}
