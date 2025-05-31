package com.cesupa.sistemadegestaohospitalar.controllers;

import com.cesupa.sistemadegestaohospitalar.controllers.ConsultaController;
import com.cesupa.sistemadegestaohospitalar.controllers.PacienteController;
import com.cesupa.sistemadegestaohospitalar.entities.Consulta;
import com.cesupa.sistemadegestaohospitalar.entities.Medico;
import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

@Component
@Profile("!test")
public class HospitalUi implements CommandLineRunner {

    @Autowired
    private PacienteController pacienteController;

    @Autowired
    private ConsultaController consultaController;

    @Autowired
    private MedicoRepository medicoRepository;

    private Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== SISTEMA DE GESTÃO HOSPITALAR ===");

        while (true) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> cadastrarNovoPaciente();
                case 2 -> consultarDadosPaciente();
                case 3 -> atualizarDadosPaciente();
                case 4 -> inativarCadastroPaciente();
                case 5 -> agendarConsulta();
                case 0 -> {
                    System.out.println("Encerrando sistema...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Cadastrar Novo Paciente");
        System.out.println("2. Consultar Dados de Paciente");
        System.out.println("3. Atualizar Dados de Paciente");
        System.out.println("4. Inativar Cadastro de Paciente");
        System.out.println("5. Agendar Consulta");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void cadastrarNovoPaciente() {
        System.out.println("\n=== CADASTRAR NOVO PACIENTE ===");

        try {
            System.out.print("Nome completo: ");
            String nome = scanner.nextLine();

            System.out.print("CPF (apenas números): ");
            String cpf = scanner.nextLine();

            System.out.print("Data de nascimento (dd/mm/yyyy): ");
            LocalDate dataNascimento = LocalDate.parse(scanner.nextLine(), dateFormatter);

            // Opções de sexo
            System.out.println("\nSelecione o sexo:");
            System.out.println("1. MASCULINO");
            System.out.println("2. FEMININO");
            System.out.println("3. OUTRO");
            System.out.print("Escolha uma opção (1-3): ");

            int opcaoSexo = Integer.parseInt(scanner.nextLine());
            String sexo;
            switch (opcaoSexo) {
                case 1:
                    sexo = "MASCULINO";
                    break;
                case 2:
                    sexo = "FEMININO";
                    break;
                case 3:
                    sexo = "OUTRO";
                    break;
                default:
                    throw new IllegalArgumentException("Opção inválida para sexo");
            }

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Endereço: ");
            String endereco = scanner.nextLine();

            Paciente paciente = new Paciente(nome, cpf, dataNascimento, sexo, telefone, endereco);

            // Campos opcionais
            System.out.print("Email (opcional): ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) {
                paciente.setEmail(email);
            }

            // Opções de tipo sanguíneo
            System.out.println("\nTipo sanguíneo (opcional):");
            System.out.println("0. Pular (não informar)");
            System.out.println("1. A+");
            System.out.println("2. A-");
            System.out.println("3. B+");
            System.out.println("4. B-");
            System.out.println("5. AB+");
            System.out.println("6. AB-");
            System.out.println("7. O+");
            System.out.println("8. O-");
            System.out.print("Escolha uma opção (0-8): ");

            String inputTipoSanguineo = scanner.nextLine();
            if (!inputTipoSanguineo.trim().isEmpty()) {
                int opcaoTipo = Integer.parseInt(inputTipoSanguineo);
                String tipoSanguineo;
                switch (opcaoTipo) {
                    case 0:
                        tipoSanguineo = null;
                        break;
                    case 1:
                        tipoSanguineo = "A_POSITIVO";
                        break;
                    case 2:
                        tipoSanguineo = "A_NEGATIVO";
                        break;
                    case 3:
                        tipoSanguineo = "B_POSITIVO";
                        break;
                    case 4:
                        tipoSanguineo = "B_NEGATIVO";
                        break;
                    case 5:
                        tipoSanguineo = "AB_POSITIVO";
                        break;
                    case 6:
                        tipoSanguineo = "AB_NEGATIVO";
                        break;
                    case 7:
                        tipoSanguineo = "O_POSITIVO";
                        break;
                    case 8:
                        tipoSanguineo = "O_NEGATIVO";
                        break;
                    default:
                        throw new IllegalArgumentException("Opção inválida para tipo sanguíneo");
                }

                if (tipoSanguineo != null) {
                    paciente.setTipoSanguineo(tipoSanguineo);
                }
            }

            Paciente pacienteSalvo = pacienteController.cadastrarNovoPaciente(paciente);
            System.out.println("✓ Paciente cadastrado com sucesso!");
            System.out.println("ID: " + pacienteSalvo.getId());
            System.out.println("Nome: " + pacienteSalvo.getNome());

        } catch (NumberFormatException e) {
            System.out.println("❌ Erro: Opção inválida. Digite apenas números.");
        } catch (DateTimeParseException e) {
            System.out.println("❌ Erro: Data inválida. Use o formato dd/mm/yyyy");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void consultarDadosPaciente() {
        System.out.println("\n=== CONSULTAR DADOS DE PACIENTE ===");
        System.out.println("1. Buscar por nome");
        System.out.println("2. Buscar por CPF");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Listar todos");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao();
        String termo = "";

        switch (opcao) {
            case 1 -> {
                System.out.print("Digite o nome (ou parte): ");
                termo = scanner.nextLine();
            }
            case 2 -> {
                System.out.print("Digite o CPF: ");
                termo = scanner.nextLine();
            }
            case 3 -> {
                System.out.print("Digite o ID: ");
                String id = scanner.nextLine();
                try {
                    UUID uuid = UUID.fromString(id);
                    Optional<Paciente> paciente = pacienteController.consultarPacientePorId(uuid);
                    if (paciente.isPresent()) {
                        exibirDadosPaciente(paciente.get());
                    } else {
                        System.out.println("❌ Paciente não encontrado.");
                    }
                    return;
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ ID inválido.");
                    return;
                }
            }
            case 4 -> termo = "";
            default -> {
                System.out.println("❌ Opção inválida.");
                return;
            }
        }

        List<Paciente> pacientes = pacienteController.consultarPacientes(termo);

        if (pacientes.isEmpty()) {
            System.out.println("❌ Nenhum paciente encontrado.");
        } else {
            System.out.println("\n=== PACIENTES ENCONTRADOS ===");
            for (int i = 0; i < pacientes.size(); i++) {
                System.out.println("\n--- Paciente " + (i + 1) + " ---");
                exibirDadosPaciente(pacientes.get(i));
            }
        }
    }

    private void exibirDadosPaciente(Paciente paciente) {
        System.out.println("ID: " + paciente.getId());
        System.out.println("Nome: " + paciente.getNome());
        System.out.println("CPF: " + paciente.getCpf());
        System.out.println("Data de Nascimento: " + paciente.getDataNascimento().format(dateFormatter));
        System.out.println("Sexo: " + paciente.getSexo());
        System.out.println("Telefone: " + (paciente.getTelefone() != null ? paciente.getTelefone() : "Não informado"));
        System.out.println("Endereço: " + (paciente.getEndereco() != null ? paciente.getEndereco() : "Não informado"));
        System.out.println("Email: " + (paciente.getEmail() != null ? paciente.getEmail() : "Não informado"));
        System.out.println("Tipo Sanguíneo: " + (paciente.getTipoSanguineo() != null ? paciente.getTipoSanguineo() : "Não informado"));
        System.out.println("Status: " + paciente.getStatus());
        if (paciente.getJustificaStatus() != null) {
            System.out.println("Justificativa Status: " + paciente.getJustificaStatus());
        }
    }

    private void atualizarDadosPaciente() {
        System.out.println("\n=== ATUALIZAR DADOS DE PACIENTE ===");
        System.out.print("Digite o ID do paciente: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Optional<Paciente> pacienteOpt = pacienteController.consultarPacientePorId(id);

            if (pacienteOpt.isEmpty()) {
                System.out.println("❌ Paciente não encontrado.");
                return;
            }

            Paciente paciente = pacienteOpt.get();
            System.out.println("\n=== DADOS ATUAIS ===");
            exibirDadosPaciente(paciente);

            System.out.println("\n=== EDITAR DADOS ===");
            System.out.println("Pressione ENTER para manter o valor atual");

            System.out.print("Nome (" + paciente.getNome() + "): ");
            String nome = scanner.nextLine();
            if (!nome.trim().isEmpty()) {
                paciente.setNome(nome);
            }

            System.out.print("Data de nascimento (" + paciente.getDataNascimento().format(dateFormatter) + "): ");
            String dataStr = scanner.nextLine();
            if (!dataStr.trim().isEmpty()) {
                try {
                    paciente.setDataNascimento(LocalDate.parse(dataStr, dateFormatter));
                } catch (DateTimeParseException e) {
                    System.out.println("⚠️ Data inválida, mantendo valor anterior.");
                }
            }

            System.out.print("Sexo (" + paciente.getSexo() + "): ");
            System.out.println("\nSelecione o sexo:");
            System.out.println("1. MASCULINO");
            System.out.println("2. FEMININO");
            System.out.println("3. OUTRO");
            System.out.print("Escolha uma opção (1-3): ");

            int opcaoSexo = Integer.parseInt(scanner.nextLine());
            String sexo;
            switch (opcaoSexo) {
                case 1:
                    sexo = "MASCULINO";
                    break;
                case 2:
                    sexo = "FEMININO";
                    break;
                case 3:
                    sexo = "OUTRO";
                    break;
                default:
                    throw new IllegalArgumentException("Opção inválida para sexo");
            }
            if (!sexo.trim().isEmpty()) {
                paciente.setSexo(sexo);
            }

            System.out.print("Telefone (" + (paciente.getTelefone() != null ? paciente.getTelefone() : "Não informado") + "): ");
            String telefone = scanner.nextLine();
            if (!telefone.trim().isEmpty()) {
                paciente.setTelefone(telefone);
            }

            System.out.print("Endereço (" + (paciente.getEndereco() != null ? paciente.getEndereco() : "Não informado") + "): ");
            String endereco = scanner.nextLine();
            if (!endereco.trim().isEmpty()) {
                paciente.setEndereco(endereco);
            }

            System.out.print("Email (" + (paciente.getEmail() != null ? paciente.getEmail() : "Não informado") + "): ");
            String email = scanner.nextLine();
            if (!email.trim().isEmpty()) {
                paciente.setEmail(email);
            }

            System.out.print("Tipo sanguíneo (" + (paciente.getTipoSanguineo() != null ? paciente.getTipoSanguineo() : "Não informado") + "): ");
            System.out.println("\nTipo sanguíneo (opcional):");
            System.out.println("0. Pular (não informar)");
            System.out.println("1. A+");
            System.out.println("2. A-");
            System.out.println("3. B+");
            System.out.println("4. B-");
            System.out.println("5. AB+");
            System.out.println("6. AB-");
            System.out.println("7. O+");
            System.out.println("8. O-");
            System.out.print("Escolha uma opção (0-8): ");

            String inputTipoSanguineo = scanner.nextLine();
            if (!inputTipoSanguineo.trim().isEmpty()) {
                int opcaoTipo = Integer.parseInt(inputTipoSanguineo);
                String tipoSanguineo;
                switch (opcaoTipo) {
                    case 0:
                        tipoSanguineo = null;
                        break;
                    case 1:
                        tipoSanguineo = "A_POSITIVO";
                        break;
                    case 2:
                        tipoSanguineo = "A_NEGATIVO";
                        break;
                    case 3:
                        tipoSanguineo = "B_POSITIVO";
                        break;
                    case 4:
                        tipoSanguineo = "B_NEGATIVO";
                        break;
                    case 5:
                        tipoSanguineo = "AB_POSITIVO";
                        break;
                    case 6:
                        tipoSanguineo = "AB_NEGATIVO";
                        break;
                    case 7:
                        tipoSanguineo = "O_POSITIVO";
                        break;
                    case 8:
                        tipoSanguineo = "O_NEGATIVO";
                        break;
                    default:
                        throw new IllegalArgumentException("Opção inválida para tipo sanguíneo");
                }

                if (tipoSanguineo != null) {
                    paciente.setTipoSanguineo(tipoSanguineo);
                }
            }

            Paciente pacienteAtualizado = pacienteController.atualizarDadosPaciente(paciente);
            System.out.println("✓ Dados atualizados com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ ID inválido.");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void inativarCadastroPaciente() {
        System.out.println("\n=== INATIVAR CADASTRO DE PACIENTE ===");
        System.out.print("Digite o ID do paciente: ");
        String idStr = scanner.nextLine();

        try {
            UUID id = UUID.fromString(idStr);
            Optional<Paciente> pacienteOpt = pacienteController.consultarPacientePorId(id);

            if (pacienteOpt.isEmpty()) {
                System.out.println("❌ Paciente não encontrado.");
                return;
            }

            Paciente paciente = pacienteOpt.get();
            if ("INATIVO".equals(paciente.getStatus())) {
                System.out.println("❌ Paciente já está inativo.");
                return;
            }

            System.out.println("\n=== DADOS DO PACIENTE ===");
            exibirDadosPaciente(paciente);

            System.out.print("\nTem certeza que deseja inativar este paciente? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (!"S".equalsIgnoreCase(confirmacao)) {
                System.out.println("Operação cancelada.");
                return;
            }

            System.out.print("Motivo da inativação: ");
            String motivo = scanner.nextLine();

            pacienteController.inativarCadastroPaciente(id, motivo);
            System.out.println("✓ Paciente inativado com sucesso!");

        } catch (IllegalArgumentException e) {
            System.out.println("❌ ID inválido.");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private void agendarConsulta() {
        System.out.println("\n=== AGENDAR CONSULTA ===");

        try {
            // Buscar paciente
            System.out.print("Digite o nome ou CPF do paciente: ");
            String termoPaciente = scanner.nextLine();

            List<Paciente> pacientes = pacienteController.consultarPacientes(termoPaciente);

            if (pacientes.isEmpty()) {
                System.out.println("❌ Nenhum paciente encontrado.");
                return;
            }

            Paciente pacienteSelecionado;
            if (pacientes.size() == 1) {
                pacienteSelecionado = pacientes.get(0);
            } else {
                System.out.println("\n=== PACIENTES ENCONTRADOS ===");
                for (int i = 0; i < pacientes.size(); i++) {
                    System.out.println((i + 1) + ". " + pacientes.get(i).getNome() + " - CPF: " + pacientes.get(i).getCpf());
                }
                System.out.print("Selecione o paciente (número): ");
                int indicePaciente = lerOpcao() - 1;

                if (indicePaciente < 0 || indicePaciente >= pacientes.size()) {
                    System.out.println("❌ Seleção inválida.");
                    return;
                }
                pacienteSelecionado = pacientes.get(indicePaciente);
            }

            System.out.println("Paciente selecionado: " + pacienteSelecionado.getNome());

            // Listar médicos disponíveis
            List<Medico> medicos = medicoRepository.findAllAtivos();
            if (medicos.isEmpty()) {
                System.out.println("❌ Nenhum médico ativo encontrado.");
                return;
            }

            System.out.println("\n=== MÉDICOS DISPONÍVEIS ===");
            for (int i = 0; i < medicos.size(); i++) {
                Medico medico = medicos.get(i);
                System.out.println((i + 1) + ". Dr(a) " + medico.getNomeESobrenome() +
                        " - Especialidade: " + medico.getEspecialidade() +
                        " (ID: " + medico.getId() + ")");
            }

            System.out.print("Selecione o médico (número): ");
            int indiceMedico = lerOpcao() - 1;

            if (indiceMedico < 0 || indiceMedico >= medicos.size()) {
                System.out.println("❌ Seleção inválida.");
                return;
            }

            Medico medicoSelecionado = medicos.get(indiceMedico);
            System.out.println("Médico selecionado: Dr(a) " + medicoSelecionado.getNomeESobrenome());

            // Data e hora da consulta
            System.out.print("Data e hora da consulta (dd/mm/yyyy HH:mm): ");
            String dataHoraStr = scanner.nextLine();

            LocalDateTime dataConsulta = LocalDateTime.parse(dataHoraStr, dateTimeFormatter);

            // Agendar consulta
            Consulta consulta = consultaController.agendarNovaConsulta(
                    pacienteSelecionado.getId(),
                    medicoSelecionado.getId(),
                    dataConsulta
            );

            System.out.println("\n✓ Consulta agendada com sucesso!");
            System.out.println("ID da Consulta: " + consulta.getId());
            System.out.println("Paciente: " + consulta.getPaciente().getNome());
            System.out.println("Médico: Dr(a) " + consulta.getMedico().getNomeESobrenome());
            System.out.println("Data/Hora: " + consulta.getDataConsulta().format(dateTimeFormatter));
            System.out.println("Status: " + consulta.getStatus());

        } catch (DateTimeParseException e) {
            System.out.println("❌ Erro: Data/hora inválida. Use o formato dd/mm/yyyy HH:mm");
        } catch (Exception e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }
}
