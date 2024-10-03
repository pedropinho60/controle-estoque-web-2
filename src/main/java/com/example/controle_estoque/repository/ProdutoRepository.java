package com.example.controle_estoque.repository;

import com.example.controle_estoque.entity.Loja;
import com.example.controle_estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContaining(String nome);
    List<Produto> findByQuantidadeEstoqueBetween(int min, int max);
    List<Produto> findByLojaAndNomeContaining(Loja loja, String nome);
    List<Produto> findByLojaAndQuantidadeEstoqueBetween(Loja loja, int min, int max);
    List<Produto> findByLoja(Loja loja);
}
