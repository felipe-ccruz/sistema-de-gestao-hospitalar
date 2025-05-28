package com.cesupa.sistemadegestaohospitalar.repositories.funcionarioRepository;

import com.cesupa.sistemadegestaohospitalar.entities.funcionario.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {

    Optional<Medico> findByCrm(String crm);

    Optional<Medico> findByCpf(String cpf);

    List<Medico> findByEspecialidade(String especialidade);
}
