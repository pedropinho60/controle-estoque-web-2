package com.example.controle_estoque.repository;

import com.example.controle_estoque.entity.Loja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LojaRepository extends JpaRepository<Loja, Long> {
}
