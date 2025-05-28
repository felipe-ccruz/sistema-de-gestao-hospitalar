package com.cesupa.sistemadegestaohospitalar;

import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import com.cesupa.sistemadegestaohospitalar.entities.enums.Sexo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class SistemaDeGestaoHospitalarApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SistemaDeGestaoHospitalarApplication.class, args);
        System.out.println("CRIADO COM SUCESSO");
    }

    @Override
    public void run(String... args) {
        Paciente p = new Paciente();
        p.setNome("Priscila Melo");

        System.out.println(p.getNome());
    }
}
