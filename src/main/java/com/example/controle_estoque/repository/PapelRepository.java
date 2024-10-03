package com.example.controle_estoque.repository;

import com.example.controle_estoque.entity.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PapelRepository extends JpaRepository<Papel, Long> {
    Optional<Papel> findByNome(String nome);
}
