package com.cesupa.sistemadegestaohospitalar.repositories;

import com.cesupa.sistemadegestaohospitalar.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {

    Optional<Paciente> findByCpf(String cpf);

    @Query("SELECT p FROM Paciente p WHERE p.nome LIKE %:nome% AND p.status = 'ATIVO'")
    List<Paciente> findByNomeContainingIgnoreCaseAndStatusAtivo(@Param("nome") String nome);

    @Query("SELECT p FROM Paciente p WHERE p.status = 'ATIVO'")
    List<Paciente> findAllAtivos();

    boolean existsByCpf(String cpf);
}
