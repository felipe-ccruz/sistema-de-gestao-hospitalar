package com.cesupa.sistemadegestaohospitalar.service;

import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.repositories.ConsultaRepository;
import com.cesupa.sistemadegestaohospitalar.repositories.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;
    private UUID pacienteId;

    @BeforeEach
    void setUp() {
        pacienteId = UUID.randomUUID();
        paciente = new Paciente();
        paciente.setId(pacienteId);
        paciente.setNome("João da Silva");
        paciente.setCpf("12345678909");
        paciente.setStatus("ATIVO");
    }

    @Test
    void cadastrarPaciente_DeveRetornarPaciente_QuandoDadosValidos() {
        // Arrange
        when(pacienteRepository.existsByCpf(paciente.getCpf())).thenReturn(false);
        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        // Act
        Paciente resultado = pacienteService.cadastrarPaciente(paciente);

        // Assert
        assertNotNull(resultado);
        assertEquals(paciente.getNome(), resultado.getNome());
        verify(pacienteRepository).existsByCpf(paciente.getCpf());
        verify(pacienteRepository).save(paciente);
    }

    @Test
    void cadastrarPaciente_DeveLancarExcecao_QuandoCpfExistente() {
        // Arrange
        when(pacienteRepository.existsByCpf(paciente.getCpf())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> pacienteService.cadastrarPaciente(paciente));

        assertEquals("CPF já cadastrado no sistema", exception.getMessage());
        verify(pacienteRepository, never()).save(any());
    }

    @Test
    void buscarPacientes_DeveRetornarLista_QuandoTermoExistente() {
        // Arrange
        String termo = "João";
        List<Paciente> pacientes = Arrays.asList(paciente);
        when(pacienteRepository.findByNomeContainingIgnoreCaseAndStatusAtivo(termo)).thenReturn(pacientes);

        // Act
        List<Paciente> resultado = pacienteService.buscarPacientes(termo);

        // Assert
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(pacienteRepository).findByNomeContainingIgnoreCaseAndStatusAtivo(termo);
    }

    @Test
    void buscarPacientes_DeveRetornarListaVazia_QuandoTermoNaoExistente() {
        // Arrange
        String termo = "Inexistente";
        when(pacienteRepository.findByNomeContainingIgnoreCaseAndStatusAtivo(termo)).thenReturn(Arrays.asList());

        // Act
        List<Paciente> resultado = pacienteService.buscarPacientes(termo);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(pacienteRepository).findByNomeContainingIgnoreCaseAndStatusAtivo(termo);
    }

    @Test
    void buscarPorId_DeveRetornarPaciente_QuandoIdExistente() {
        // Arrange
        when(pacienteRepository.findById(pacienteId)).thenReturn(Optional.of(paciente));

        // Act
        Optional<Paciente> resultado = pacienteService.buscarPorId(pacienteId);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(paciente.getNome(), resultado.get().getNome());
        verify(pacienteRepository).findById(pacienteId);
    }

    @Test
    void buscarPorId_DeveRetornarVazio_QuandoIdNaoExistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();
        when(pacienteRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act
        Optional<Paciente> resultado = pacienteService.buscarPorId(idInexistente);

        // Assert
        assertTrue(resultado.isEmpty());
        verify(pacienteRepository).findById(idInexistente);
    }

    @Test
    void atualizarPaciente_DeveRetornarPacienteAtualizado_QuandoDadosValidos() {
        // Arrange
        Paciente dadosAtualizados = new Paciente();
        dadosAtualizados.setId(pacienteId);
        dadosAtualizados.setNome("João Silva Updated");

        when(pacienteRepository.existsById(pacienteId)).thenReturn(true);
        when(pacienteRepository.save(dadosAtualizados)).thenReturn(dadosAtualizados);

        // Act
        Paciente resultado = pacienteService.atualizarPaciente(dadosAtualizados);

        // Assert
        assertEquals("João Silva Updated", resultado.getNome());
        verify(pacienteRepository).existsById(pacienteId);
        verify(pacienteRepository).save(dadosAtualizados);
    }

    @Test
    void atualizarPaciente_DeveLancarExcecao_QuandoPacienteNaoExistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();
        Paciente pacienteInexistente = new Paciente();
        pacienteInexistente.setId(idInexistente);

        when(pacienteRepository.existsById(idInexistente)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> pacienteService.atualizarPaciente(pacienteInexistente));

        verify(pacienteRepository, never()).save(any());
    }

    @Test
    void inativarPaciente_DeveAtualizarStatus_QuandoPacienteExistente() {
        // Arrange
        String motivo = "Alta médica";
        // ❌ Removido: when(pacienteRepository.existsById(pacienteId)).thenReturn(true);
        when(pacienteRepository.findById(pacienteId)).thenReturn(Optional.of(paciente));
        when(consultaRepository.findConsultasAgendadasByPaciente(pacienteId))
                .thenReturn(Collections.emptyList()); // Adicione se ainda não tiver

        // Act
        pacienteService.inativarPaciente(pacienteId, motivo);

        // Assert
        // ❌ Removido: verify(pacienteRepository).existsById(pacienteId);
        verify(pacienteRepository).findById(pacienteId);
        verify(consultaRepository).findConsultasAgendadasByPaciente(pacienteId); // Se aplicável
        verify(pacienteRepository).save(any(Paciente.class));

        // Verificar se o objeto foi modificado corretamente
        assertEquals("INATIVO", paciente.getStatus());
        assertEquals(motivo, paciente.getJustificaStatus());
    }
    @Test
    void inativarPaciente_DeveLancarExcecao_QuandoPacienteNaoExistente() {
        // Arrange
        UUID idInexistente = UUID.randomUUID();
        String motivo = "Alta médica";
        // Removido: when(pacienteRepository.existsById(idInexistente)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> pacienteService.inativarPaciente(idInexistente, motivo));
        verify(pacienteRepository, never()).save(any());
    }
}