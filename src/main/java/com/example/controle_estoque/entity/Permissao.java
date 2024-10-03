package com.example.controle_estoque.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @ManyToMany(mappedBy = "permissoes")
    private Set<Papel> papeis;

    public Permissao() {
    }

    public Permissao(Long id, String nome, Set<Papel> papeis) {
        this.id = id;
        this.nome = nome;
        this.papeis = papeis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(Set<Papel> papeis) {
        this.papeis = papeis;
    }
}
