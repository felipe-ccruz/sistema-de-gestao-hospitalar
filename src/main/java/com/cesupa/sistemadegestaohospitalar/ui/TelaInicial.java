package com.cesupa.sistemadegestaohospitalar.ui;

import java.util.Scanner;

public class TelaInicial {
    private Scanner sc = new Scanner(System.in);

    public void exibirTelaInicial() {
        limparTela();
        exibirLogoGrande();
        sc.nextLine();

        //LOGIN
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.preencherInformacoes();



        sc.close();
    }

    private void exibirLogoGrande() {
        System.out.println("\n\n");
        System.out.println("   _____ _        _   _     _                         _ ");
        System.out.println("  / ____| |      | | (_)   | |                       | |");
        System.out.println(" | (___ | |_ __ _| |_ _ ___| |_ ___ _ __   __ _ _ __ | |");
        System.out.println("  \\___ \\| __/ _` | __| / __| __/ _ \\ '_ \\ / _` | '_ \\| |");
        System.out.println("  ____) | || (_| | |_| \\__ \\ ||  __/ | | | (_| | | | | |");
        System.out.println(" |_____/ \\__\\__,_|\\__|_|___/\\__\\___|_| |_|\\__,_|_| |_|_|");
        System.out.println("\n        S I S T E M A   D E   G E S T Ã O");
        System.out.println("           H O S P I T A L A R");
        System.out.println("\n\n        Pressione ENTER para continuar...");
    }

    private void limparTela() {
        // Simula limpeza da tela com várias quebras de linha
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}