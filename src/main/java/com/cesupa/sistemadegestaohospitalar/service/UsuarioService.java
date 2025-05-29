package com.cesupa.sistemadegestaohospitalar.service;

import com.cesupa.sistemadegestaohospitalar.entities.Usuario;
import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import com.cesupa.sistemadegestaohospitalar.entities.funcionario.Recepcionista;
import com.cesupa.sistemadegestaohospitalar.repositories.funcionarioRepository.RecepcionistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cesupa.sistemadegestaohospitalar.repositories.UsuarioRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RecepcionistaRepository recepcionistaRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, RecepcionistaRepository recepcionistaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.recepcionistaRepository = recepcionistaRepository;
    }

    public boolean validarLogin(String login, String senha) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findByLogin(login);

            if (usuarioOpt.isEmpty()) {
                System.out.println("Erro: Usuário não encontrado.");
                return false;
            }

            Usuario usuario = usuarioOpt.get();

            if (usuario.getSenha().equals(senha)) {
                return true;
            } else {
                System.out.println("Erro: Senha incorreta.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro durante a validação de login: " + e.getMessage());
            return false;
        }
    }

    //FUNÇÕES DO ADMIN
    public boolean cadastrarUsuario(String login, String senha, Usuario.PerfilUsuario perfil) {
        try {
            if (usuarioRepository.existsByLogin(login)) {
                System.out.println("Erro: Login já existe!");
                return false;
            }

            Usuario novoUsuario = new Usuario();
            novoUsuario.setLogin(login);
            novoUsuario.setSenha(senha); // Sem criptografia
            novoUsuario.setPerfilUsuario(perfil);

            usuarioRepository.save(novoUsuario);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }


    public boolean criarNovoRecepcionista(String nome, String cpf, LocalDate dataNascimento, Sexo sexo, Recepcionista.Turno turno, String login, String senha) {
        try {
            if (usuarioRepository.existsByLogin(login)) {
                System.out.println("Erro: Login já existe!");
                return false;
            }

            if (!ValidadorUtil.validarCPF(cpf)) {
                System.out.println("Erro: CPF inválido!");
                return false;
            }

            Recepcionista novoRecepcionista = new Recepcionista();
            novoRecepcionista.setNome(nome);
            novoRecepcionista.setCpf(cpf);
            novoRecepcionista.setDataNascimento(dataNascimento);
            novoRecepcionista.setSexo(sexo);
            novoRecepcionista.setTurno(turno);

            Usuario novoUsuario = new Usuario();
            novoUsuario.setLogin(login);
            novoUsuario.setSenha(senha);
            novoUsuario.setPerfilUsuario(Usuario.PerfilUsuario.RECEPCIONISTA);

            novoRecepcionista.setUsuario(novoUsuario);

            recepcionistaRepository.save(novoRecepcionista);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar recepcionista: " + e.getMessage());
            return false;
        }
    }

    public class ValidadorUtil {
        public static boolean validarCPF(String cpf) {
            // Implementação da validação de CPF
            cpf = cpf.replaceAll("[^0-9]", "");
            return cpf.length() == 11; // Implemente a validação completa aqui
        }
    }
}
