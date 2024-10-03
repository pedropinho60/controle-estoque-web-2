package com.example.controle_estoque.service;

import com.example.controle_estoque.entity.Loja;
import com.example.controle_estoque.entity.Operacao;
import com.example.controle_estoque.entity.Produto;
import com.example.controle_estoque.exceptions.EstoqueInsuficienteException;
import com.example.controle_estoque.exceptions.LojaNaoEncontradaException;
import com.example.controle_estoque.exceptions.ProdutoNaoEncontradoException;
import com.example.controle_estoque.repository.LojaRepository;
import com.example.controle_estoque.repository.OperacaoRepository;
import com.example.controle_estoque.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OperacaoService {
    private final OperacaoRepository operacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final LojaRepository lojaRepository;

    @Autowired
    public OperacaoService(OperacaoRepository operacaoRepository, ProdutoRepository produtoRepository, LojaRepository lojaRepository) {
        this.operacaoRepository = operacaoRepository;
        this.produtoRepository = produtoRepository;
        this.lojaRepository = lojaRepository;
    }

    public void registrarEntrada(Long produtoId, Integer quantidade) {
        Produto produto = produtoRepository.findById(produtoId).orElseThrow();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
        produtoRepository.save(produto);

        Operacao operacao = new Operacao();
        operacao.setProduto(produto);
        operacao.setQuantidade(quantidade);
        operacao.setDataOperacao(LocalDateTime.now());
        operacao.setTipoOperacao("ENTRADA");
        operacaoRepository.save(operacao);
    }

    public void registrarSaida(Long produtoId, Integer quantidade) {
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado."));

        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para realizar a operação.");
        }

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        produtoRepository.save(produto);

        Operacao operacao = new Operacao();
        operacao.setProduto(produto);
        operacao.setQuantidade(quantidade);
        operacao.setDataOperacao(LocalDateTime.now());
        operacao.setTipoOperacao("SAIDA");
        operacaoRepository.save(operacao);
    }

    public List<Operacao> historico(String dataInicio, String dataFim) {
        LocalDateTime inicio = LocalDateTime.parse(dataInicio);
        LocalDateTime fim = LocalDateTime.parse(dataFim);

        return operacaoRepository.findByDataOperacaoBetween(inicio, fim);
    }

    public List<Operacao> historicoLoja(Long lojaId, String dataInicio, String dataFim) {
        Loja loja = lojaRepository.findById(lojaId).orElseThrow(() -> new LojaNaoEncontradaException("Loja não encontrada."));

        LocalDateTime inicio = LocalDateTime.parse(dataInicio);
        LocalDateTime fim = LocalDateTime.parse(dataFim);

        return operacaoRepository.findByLojaAndDataOperacaoBetween(loja, inicio, fim);
    }
}
