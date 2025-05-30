package com.cesupa.sistemadegestaohospitalar.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cesupa.sistemadegestaohospitalar.entities.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {

    @Query("SELECT m FROM Medico m WHERE m.status = 'ATIVO'")
    List<Medico> findAllAtivos();
}