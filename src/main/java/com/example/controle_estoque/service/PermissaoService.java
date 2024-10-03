package com.example.controle_estoque.service;

import com.example.controle_estoque.entity.Permissao;
import com.example.controle_estoque.exceptions.PermissaoNaoEncontradaException;
import com.example.controle_estoque.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissaoService {
    private final PermissaoRepository permissaoRepository;

    @Autowired
    public PermissaoService(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    public Permissao cadastrarPermissao(Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    public List<Permissao> listarPermissoes() {
        return permissaoRepository.findAll();
    }

    public Permissao consultarPorId(Long id) {
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException("Permissão não encontrada."));
    }

    public Permissao atualizarPermissao(Long id, Permissao permissao) {
        Permissao permissaoSalva = permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException("Permissão não encontrada."));

        permissaoSalva.setNome(permissao.getNome());

        return permissaoRepository.save(permissaoSalva);
    }

    public void deletarPermissao(Long id) {
        Permissao permissao = permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException("Permissão não encontrada."));

        permissaoRepository.delete(permissao);
    }
}
