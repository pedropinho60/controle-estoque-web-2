package com.example.controle_estoque.controller;

import com.example.controle_estoque.entity.Loja;
import com.example.controle_estoque.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lojas")
public class LojaController {
    private final LojaService lojaService;

    @Autowired
    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @PreAuthorize("hasAuthority('consultar_lojas')")
    @GetMapping("/consulta")
    public List<Loja> consultarLojas() {
        return lojaService.consultarTodas();
    }

    @PreAuthorize("hasAuthority('consultar_lojas')")
    @GetMapping("/consulta/{id}")
    public Loja consultarLojaId(@PathVariable Long id) {
        return lojaService.consultarPorId(id);
    }

    @PreAuthorize("hasAuthority('cadastrar_lojas')")
    @PostMapping("/cadastrar")
    public Loja cadastrarLoja(@RequestBody Loja loja) {
        return lojaService.cadastrarLoja(loja);
    }

    @PreAuthorize("hasAuthority('cadastrar_lojas')")
    @PutMapping("/atualizar/{id}")
    public Loja atualizarLoja(@PathVariable Long id, @RequestBody Loja loja) {
        return lojaService.atualizarLoja(id, loja);
    }

    @PreAuthorize("hasAuthority('remover_lojas')")
    @DeleteMapping("/remover/{id}")
    public void deletarLoja(@PathVariable Long id) {
        lojaService.deletarLoja(id);
    }
}
