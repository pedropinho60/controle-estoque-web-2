package com.example.controle_estoque.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Operacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Loja loja;

    private Integer quantidade;
    private LocalDateTime dataOperacao;
    private String tipoOperacao;

    public Operacao() {
    }

    public Operacao(Long id, Produto produto, Integer quantidade, LocalDateTime dataOperacao, String tipoOperacao) {
        this.id = id;
        this.produto = produto;
        this.loja = produto.getLoja();
        this.quantidade = quantidade;
        this.dataOperacao = dataOperacao;
        this.tipoOperacao = tipoOperacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        this.loja = produto.getLoja();
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(LocalDateTime dataOperacao) {
        this.dataOperacao = dataOperacao;
    }

    public String getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(String tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Loja getLoja() {
        return loja;
    }
}
