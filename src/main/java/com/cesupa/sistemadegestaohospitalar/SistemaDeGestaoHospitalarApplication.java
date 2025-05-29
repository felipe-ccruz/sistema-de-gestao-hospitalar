package com.cesupa.sistemadegestaohospitalar;


import com.cesupa.sistemadegestaohospitalar.ui.TelaInicial;
import com.cesupa.sistemadegestaohospitalar.ui.TelaLogin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class SistemaDeGestaoHospitalarApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SistemaDeGestaoHospitalarApplication.class);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        TelaInicial telaInicial = new TelaInicial();
        telaInicial.exibirTelaInicial();
    }
}
