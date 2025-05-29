package com.cesupa.sistemadegestaohospitalar.ui;

import java.util.Scanner;

public class TelaLogin {
    private Scanner sc = new Scanner(System.in);

    public void preencherInformacoes() {
        System.out.println("\n\n");
        System.out.println("====================================");
        System.out.println("    SISTEMA DE GESTÃO HOSPITALAR    ");
        System.out.println("====================================");
        System.out.println("\n");

        System.out.print("Usuário: ");
        String usuario = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        System.out.println("\n");
        System.out.println("====================================");
    }
}