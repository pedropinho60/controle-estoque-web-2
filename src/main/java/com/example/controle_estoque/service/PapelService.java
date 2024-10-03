package com.example.controle_estoque.service;

import com.example.controle_estoque.entity.Papel;
import com.example.controle_estoque.exceptions.PapelNaoEncontradoException;
import com.example.controle_estoque.exceptions.PermissaoNaoEncontradaException;
import com.example.controle_estoque.repository.PapelRepository;
import com.example.controle_estoque.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PapelService {
    private final PapelRepository papelRepository;
    private final PermissaoRepository permissaoRepository;

    @Autowired
    public PapelService(PapelRepository papelRepository, PermissaoRepository permissaoRepository) {
        this.papelRepository = papelRepository;
        this.permissaoRepository = permissaoRepository;
    }

    public Papel cadastrarPapel(Papel papel) {
        return papelRepository.save(papel);
    }

    public List<Papel> listarPapeis() {
        return papelRepository.findAll();
    }

    public Papel consultarPorId(Long id) {
        return papelRepository.findById(id)
                .orElseThrow(() -> new PapelNaoEncontradoException("Papel não encontrado."));
    }

    public Papel adicionarPermissao(Long papelId, Long permissaoId) {
        Papel papel = papelRepository.findById(papelId)
                .orElseThrow(() -> new PapelNaoEncontradoException("Papel não encontrado."));

        papel.getPermissoes().add(permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException("Permissão não encontrada.")));

        return papelRepository.save(papel);
    }

    public Papel atualizarPapel(Long id, Papel papel) {
        Papel papelSalvo = papelRepository.findById(id)
                .orElseThrow(() -> new PapelNaoEncontradoException("Papel não encontrado."));

        papelSalvo.setNome(papel.getNome());
        papelSalvo.setPermissoes(papel.getPermissoes());

        return papelRepository.save(papelSalvo);
    }

    public void deletarPapel(Long id) {
        Papel papel = papelRepository.findById(id)
                .orElseThrow(() -> new PapelNaoEncontradoException("Papel não encontrado."));

        papelRepository.delete(papel);
    }
}
