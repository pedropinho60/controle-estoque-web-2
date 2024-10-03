package com.example.controle_estoque.controller;

import com.example.controle_estoque.entity.Permissao;
import com.example.controle_estoque.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {
    private final PermissaoService permissaoService;

    @Autowired
    public PermissaoController(PermissaoService permissaoService) {
        this.permissaoService = permissaoService;
    }

    @PreAuthorize("hasAuthority('cadastrar_permissao')")
    @PostMapping("/cadastrar")
    public ResponseEntity<Permissao> criarPermissao(@RequestBody Permissao permissao) {
        Permissao novaPermissao = permissaoService.cadastrarPermissao(permissao);
        return ResponseEntity.ok(novaPermissao);
    }

    @PreAuthorize("hasAuthority('consultar_permissao')")
    @GetMapping("/listar")
    public ResponseEntity<List<Permissao>> listarPermissoes() {
        List<Permissao> permissoes = permissaoService.listarPermissoes();
        return ResponseEntity.ok(permissoes);
    }

    @PreAuthorize("hasAuthority('consultar_permissao')")
    @GetMapping("/listar/{id}")
    public ResponseEntity<Permissao> buscarPermissaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(permissaoService.consultarPorId(id));
    }

    @PreAuthorize("hasAuthority('remover_permissao')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deletarPermissao(@PathVariable Long id) {
        permissaoService.deletarPermissao(id);
        return ResponseEntity.noContent().build();
    }
}
