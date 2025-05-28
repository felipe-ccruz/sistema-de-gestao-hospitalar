package com.cesupa.sistemadegestaohospitalar.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoConfirmar;

    public TelaLogin() {
        super("Login - Sistema Hospitalar");

        // Configurações básicas da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 180);
        setLocationRelativeTo(null); // Centraliza a janela

        // Criar painel principal
        JPanel painel = new JPanel();
        painel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Label e campo Login
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(new JLabel("Login:"), gbc);

        campoLogin = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 10, 10);
        painel.add(campoLogin, gbc);

        // Label e campo Senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 5, 10);
        painel.add(new JLabel("Senha:"), gbc);

        campoSenha = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 10, 10);
        painel.add(campoSenha, gbc);

        // Botão Confirmar
        botaoConfirmar = new JButton("Confirmar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(botaoConfirmar, gbc);

        // Adiciona ação no botão
        botaoConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = campoLogin.getText();
                String senha = new String(campoSenha.getPassword());

                // Aqui você pode chamar seu método de validação/autenticação
                JOptionPane.showMessageDialog(TelaLogin.this,
                        "Login: " + login + "\nSenha: " + senha,
                        "Dados informados",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(painel);
    }
}