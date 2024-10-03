package com.example.controle_estoque.controller;

import com.example.controle_estoque.service.ProdutoService;
import com.example.controle_estoque.entity.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    @Autowired
    ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PreAuthorize("hasAuthority('consultar_produtos')")
    @GetMapping("/{id}")
    public ResponseEntity<Produto> consultarProdutoId(@PathVariable Long id) {
        Produto produto = produtoService.consultarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PreAuthorize("hasAuthority('consultar_produtos')")
    @GetMapping("/consulta")
    public ResponseEntity<List<Produto>> consultarProdutos(@RequestParam(required = false) Long lojaId,
                                                           @RequestParam(required = false) String nome,
                                                           @RequestParam(required = false) Integer minQuantidade,
                                                           @RequestParam(required = false) Integer maxQuantidade) {

        List<Produto> produtos;

        if (nome != null) {
            if (lojaId != null) {
                produtos =  produtoService.consultarPorLojaENome(lojaId, nome);
            } else {
                produtos = produtoService.consultarPorNome(nome);
            }
        } else if (minQuantidade != null && maxQuantidade != null) {
            if (lojaId != null) {
                produtos =  produtoService.consultarPorLojaEFaixaQuantidade(lojaId, minQuantidade, maxQuantidade);
            } else {
                produtos =  produtoService.consultarPorFaixaQuantidade(minQuantidade, maxQuantidade);
            }
        } else {
            if (lojaId != null) {
                produtos =  produtoService.consultarPorLoja(lojaId);
            } else {
                produtos = produtoService.consultarTodos();
            }
        }

        return ResponseEntity.ok(produtos);
    }

    @PreAuthorize("hasAuthority('consultar_produtos')")
    @GetMapping("/baixo-estoque")
    public ResponseEntity<List<Produto>> baixoEstoque(@RequestParam Long lojaId,
                                                      @RequestParam Integer limite) {

        List<Produto> produtos;
        if (lojaId != null) {
            produtos = produtoService.produtosLojaBaixoEstoque(lojaId, limite);
        } else {
            produtos = produtoService.produtosBaixoEstoque(limite);
        }
        return ResponseEntity.ok(produtos);
    }

    @PreAuthorize("hasAuthority('cadastrar_produtos')")
    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.cadastrarProduto(produto);
        return ResponseEntity.ok(novoProduto);
    }

    @PreAuthorize("hasAuthority('cadastrar_produtos')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto produto = produtoService.atualizarProduto(id, produtoAtualizado);
        return ResponseEntity.ok(produto);
    }

    @PreAuthorize("hasAuthority('remover_produtos')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long id) {
        produtoService.removerProduto(id);
        return ResponseEntity.noContent().build();
    }

}
