package com.example.controle_estoque.service;

import com.example.controle_estoque.entity.Loja;
import com.example.controle_estoque.exceptions.LojaNaoEncontradaException;
import com.example.controle_estoque.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LojaService {
    private final LojaRepository lojaRepository;

    @Autowired
    public LojaService(LojaRepository lojaRepository) {
        this.lojaRepository = lojaRepository;
    }

    public Loja cadastrarLoja(Loja loja) {
        return lojaRepository.save(loja);
    }

    public List<Loja> consultarTodas() {
        return lojaRepository.findAll();
    }

    public Loja consultarPorId(Long id) {
        return lojaRepository.findById(id)
                .orElseThrow(() -> new LojaNaoEncontradaException("Loja não encontrada."));
    }

    public void deletarLoja(Long id) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new LojaNaoEncontradaException("Loja não encontrada."));
        lojaRepository.delete(loja);
    }

    public Loja atualizarLoja(Long id, Loja loja) {
        Loja lojaAtual = lojaRepository.findById(id)
                .orElseThrow(() -> new LojaNaoEncontradaException("Loja não encontrada."));

        lojaAtual.setNome(loja.getNome());
        lojaAtual.setEndereco(loja.getEndereco());
        return lojaRepository.save(lojaAtual);
    }


}
