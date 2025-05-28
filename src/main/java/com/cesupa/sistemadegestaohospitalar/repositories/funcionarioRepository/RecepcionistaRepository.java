package com.cesupa.sistemadegestaohospitalar.repositories.funcionarioRepository;

import com.cesupa.sistemadegestaohospitalar.entities.funcionario.Recepcionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecepcionistaRepository extends JpaRepository<Recepcionista, UUID> {

    // Buscar recepcionista por CPF (herdado de Funcionario)
    Optional<Recepcionista> findByCpf(String cpf);

    // Caso queira, pode criar busca por turno, por exemplo
    List<Recepcionista> findByTurno(Recepcionista.Turno turno);

}
