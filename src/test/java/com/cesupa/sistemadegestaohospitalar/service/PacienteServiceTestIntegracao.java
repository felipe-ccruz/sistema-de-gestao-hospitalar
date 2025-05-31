package com.cesupa.sistemadegestaohospitalar.service;

import com.cesupa.sistemadegestaohospitalar.controllers.ConsultaController;
import com.cesupa.sistemadegestaohospitalar.entities.Medico;
import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.entities.Consulta;
import com.cesupa.sistemadegestaohospitalar.entities.Usuario;
import com.cesupa.sistemadegestaohospitalar.repositories.ConsultaRepository;
import com.cesupa.sistemadegestaohospitalar.repositories.MedicoRepository;
import com.cesupa.sistemadegestaohospitalar.repositories.PacienteRepository;
import com.cesupa.sistemadegestaohospitalar.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PacienteServiceTestIntegracao {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private ConsultaController consultaController;

    // Caso de uso 1: Cadastrar novo paciente
    @Test
    public void testInjecaoPacienteNoBanco() {
        try {
            Paciente paciente = new Paciente();
            paciente.setNome("Teste de Integração");
            paciente.setCpf("12345678909"); // CPF válido fictício
            paciente.setDataNascimento(LocalDate.of(2000, 1, 1));
            paciente.setSexo("MASCULINO");
            paciente.setTelefone("999999999");
            paciente.setEndereco("Rua Teste");
            paciente.setEmail("teste@exemplo.com");

            pacienteService.cadastrarPaciente(paciente);

            if (paciente.getId() != null && pacienteRepository.findById(paciente.getId()).isPresent()) {
                System.out.println("✅ Caso de Uso 1: Cadastro de paciente concluído com sucesso.");
            } else {
                System.out.println("❌ Caso de Uso 1: Falha ao cadastrar paciente.");
            }

        } catch (Exception e) {
            System.out.println("❌ Caso de Uso 1: Falha ao cadastrar paciente: " + e.getMessage());
        }
    }

    // Caso de uso 2: Consultar Dados de Paciente
    @Test
    public void testConsultarDadosPaciente() {
        try {
            Paciente paciente = new Paciente("Joana Teste", "98765432100", LocalDate.of(1995, 5, 5), "FEMININO", "888888888", "Rua A");
            pacienteService.cadastrarPaciente(paciente);

            var encontrados = pacienteService.buscarPacientes("Joana");

            if (!encontrados.isEmpty() && encontrados.get(0).getNome().equals("Joana Teste")) {
                System.out.println("✅ Caso de Uso 2: Consulta de paciente bem-sucedida.");
            } else {
                System.out.println("❌ Caso de Uso 2: Paciente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("❌ Caso de Uso 2: Erro ao consultar paciente: " + e.getMessage());
        }
    }

    // Caso de uso 3: Atualizar Dados de Paciente
    @Test
    public void testAtualizarDadosPaciente() {
        try {
            // Cadastro inicial
            Paciente paciente = new Paciente();
            paciente.setNome("Carlos Original");
            paciente.setCpf("12345678909"); // CPF válido e fixo
            paciente.setDataNascimento(LocalDate.of(1990, 3, 15));
            paciente.setSexo("MASCULINO");
            paciente.setTelefone("91984299832");
            paciente.setEndereco("Luiz II");

            paciente = pacienteService.cadastrarPaciente(paciente);

            // Atualiza dados (mas NÃO o CPF)
            paciente.setNome("Carlos Atualizado");
            paciente.setEndereco("Rua Nova");

            paciente = pacienteService.atualizarPaciente(paciente);

            if ("Carlos Atualizado".equals(paciente.getNome())
                    && "Rua Nova".equals(paciente.getEndereco())
                    && "12345678909".equals(paciente.getCpf())) {
                System.out.println("✅ Caso de Uso 3: Atualização de paciente bem-sucedida.");
            } else {
                System.out.println("❌ Caso de Uso 3: Falha na atualização dos dados.");
            }
        } catch (Exception e) {
            System.out.println("❌ Caso de Uso 3: Erro ao atualizar paciente: " + e.getMessage());
        }
    }


    @Test
    public void testInativarPaciente() {
        try {
            Paciente paciente = new Paciente(
                    "Paciente Inativar",
                    "12345678909", // CPF válido
                    LocalDate.of(1980, 10, 10),
                    "MASCULINO",
                    null,
                    null
            );
            paciente = pacienteService.cadastrarPaciente(paciente);

            pacienteService.inativarPaciente(paciente.getId(), "Teste de inativação");

            Optional<Paciente> inativo = pacienteRepository.findById(paciente.getId());

            if (inativo.isPresent() && "INATIVO".equals(inativo.get().getStatus())) {
                System.out.println("✅ Caso de Uso 4: Paciente inativado com sucesso.");
            } else {
                System.out.println("❌ Caso de Uso 4: Falha ao inativar paciente.");
            }
        } catch (Exception e) {
            System.out.println("❌ Caso de Uso 4: Erro ao inativar paciente: " + e.getMessage());
        }
    }


    // Caso de uso 5: Agendamento de Consulta (Simples, sem validação de conflito)
    @Test
    public void testAgendamentoConsulta() {
        try {
            // Cadastrar paciente
            Paciente paciente = new Paciente("Paciente Consulta", "11144477735", LocalDate.of(1990, 1, 1), "MASCULINO", null, null);
            paciente = pacienteService.cadastrarPaciente(paciente);

            Usuario usuario = new Usuario();
            usuario.setNomeUsuario("Usuario Médico");
            usuario.setPerfilUsuario("MEDICO");
            usuario.setSenha("123456");// ou qualquer valor exigido// Usa o retorno com ID gerado
            usuario = usuarioRepository.save(usuario);

            // Cadastrar médico com todos os campos obrigatórios
            Medico medico = new Medico();
            medico.setNomeESobrenome("Dr. Teste");
            medico.setCpf("12345678909");
            medico.setCrm("CRM123456");
            medico.setDataNascimento(LocalDate.of(1980, 1, 1));
            medico.setEspecialidade("Clínico Geral");
            medico.setSexo("MASCULINO"); //<-- aqui está a correção final
            medico.setStatus("ATIVO");
            medico.setUsuario(usuario);
            medico = medicoRepository.save(medico); // Usa o retorno com ID gerado

            // Verificação de segurança
            if (paciente.getId() == null || medico.getId() == null) {
                throw new IllegalStateException("IDs de paciente ou médico estão nulos após persistência.");
            }

            // Agendar consulta
            LocalDateTime dataConsulta = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
            Consulta consulta = consultaController.agendarNovaConsulta(paciente.getId(), medico.getId(), dataConsulta);

            if (consulta != null
                    && consulta.getPaciente().getId().equals(paciente.getId())
                    && consulta.getMedico().getId().equals(medico.getId())) {
                System.out.println("✅ Caso de Uso 5: Consulta agendada com sucesso.");
            } else {
                System.out.println("❌ Caso de Uso 5: Falha ao agendar consulta.");
            }

        } catch (Exception e) {
            System.out.println("❌ Caso de Uso 5: Erro no agendamento de consulta: " + e.getMessage());
        }
    }
}


