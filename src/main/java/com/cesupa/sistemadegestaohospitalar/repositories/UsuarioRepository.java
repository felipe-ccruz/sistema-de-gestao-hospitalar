package com.cesupa.sistemadegestaohospitalar.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cesupa.sistemadegestaohospitalar.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
    boolean existsByNomeUsuario(String nomeUsuario);
}