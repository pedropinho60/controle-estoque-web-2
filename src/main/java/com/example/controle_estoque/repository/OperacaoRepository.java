package com.example.controle_estoque.repository;

import com.example.controle_estoque.entity.Loja;
import com.example.controle_estoque.entity.Operacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OperacaoRepository extends JpaRepository<Operacao, Long> {
    List<Operacao> findByDataOperacaoBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Operacao> findByLojaAndDataOperacaoBetween(Loja loja, LocalDateTime inicio, LocalDateTime fim);
}
