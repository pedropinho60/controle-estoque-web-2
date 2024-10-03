package com.example.controle_estoque.service;

import com.example.controle_estoque.entity.Loja;
import com.example.controle_estoque.entity.Produto;
import com.example.controle_estoque.exceptions.LojaNaoEncontradaException;
import com.example.controle_estoque.exceptions.ProdutoNaoEncontradoException;
import com.example.controle_estoque.repository.LojaRepository;
import com.example.controle_estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final LojaRepository lojaRepository;

    @Autowired
    ProdutoService(ProdutoRepository produtoRepository, LojaRepository lojaRepository) {
        this.produtoRepository = produtoRepository;
        this.lojaRepository = lojaRepository;
    }

    public Produto cadastrarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> consultarTodos() {
        return produtoRepository.findAll();
    }

    public Produto consultarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado."));
    }

    public List<Produto> consultarPorNome(String nome) {
        return produtoRepository.findByNomeContaining(nome);
    }

    public List<Produto> consultarPorLojaENome(Long lojaId, String nome) {
        Loja loja = lojaRepository.findById(lojaId)
                .orElseThrow(() -> new LojaNaoEncontradaException("Loja não encontrada."));

        return produtoRepository.findByLojaAndNomeContaining(loja, nome);
    }

    public List<Produto> consultarPorFaixaQuantidade(int minQuantidade, int maxQuantidade) {
        return produtoRepository.findByQuantidadeEstoqueBetween(minQuantidade, maxQuantidade);
    }

    public List<Produto> consultarPorLojaEFaixaQuantidade(Long lojaId, int minQuantidade, int maxQuantidade) {
        Loja loja = lojaRepository.findById(lojaId)
                .orElseThrow(() -> new LojaNaoEncontradaException("Loja não encontrada."));

        return produtoRepository.findByLojaAndQuantidadeEstoqueBetween(loja, minQuantidade, maxQuantidade);
    }

    public List<Produto> produtosBaixoEstoque(int limite) {
        return produtoRepository.findByQuantidadeEstoqueBetween(0, limite);
    }

    public List<Produto> produtosLojaBaixoEstoque(Long lojaId, int limite) {
        Loja loja = lojaRepository.findById(lojaId)
                .orElseThrow(() -> new LojaNaoEncontradaException("Loja não encontrada."));

        return produtoRepository.findByLojaAndQuantidadeEstoqueBetween(loja, 0, limite);
    }

    public Produto atualizarProduto(Long id, Produto produto) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado."));

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setPrecoCusto(produto.getPrecoCusto());
        produtoExistente.setPrecoVenda(produto.getPrecoVenda());
        produtoExistente.setQuantidadeEstoque(produto.getQuantidadeEstoque());

        return produtoRepository.save(produtoExistente);
    }

    public void removerProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado."));

        produtoRepository.delete(produto);
    }

    public List<Produto> consultarPorLoja(Long lojaId) {
        Loja loja = lojaRepository.findById(lojaId)
                .orElseThrow(() -> new LojaNaoEncontradaException("Loja não encontrada."));

        return produtoRepository.findByLoja(loja);
    }
}
