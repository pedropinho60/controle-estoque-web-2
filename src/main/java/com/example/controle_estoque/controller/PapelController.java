package com.example.controle_estoque.controller;

import com.example.controle_estoque.entity.Papel;
import com.example.controle_estoque.service.PapelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/papeis")
public class PapelController {
    private final PapelService papelService;

    @Autowired
    public PapelController(PapelService papelService) {
        this.papelService = papelService;
    }

    @PreAuthorize("hasAuthority('cadastrar_papel')")
    @PostMapping("/cadastrar")
    public ResponseEntity<Papel> criarPapel(@RequestBody Papel papel) {
        Papel novoPapel = papelService.cadastrarPapel(papel);
        return ResponseEntity.ok(novoPapel);
    }

    @PreAuthorize("hasAuthority('consultar_papel')")
    @GetMapping("/listar")
    public ResponseEntity<List<Papel>> listarPapeis() {
        List<Papel> papeis = papelService.listarPapeis();
        return ResponseEntity.ok(papeis);
    }

    @PreAuthorize("hasAuthority('consultar_papel')")
    @GetMapping("/listar/{id}")
    public ResponseEntity<Papel> buscarPapelPorId(@PathVariable Long id) {
        return ResponseEntity.ok(papelService.consultarPorId(id));
    }

    @PreAuthorize("hasAuthority('cadastrar_papel')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Papel> atualizarPapel(@PathVariable Long id, @RequestBody Papel papelAtualizado) {
        Papel papel = papelService.atualizarPapel(id, papelAtualizado);
        return ResponseEntity.ok(papel);
    }

    @PreAuthorize("hasAuthority('remover_papel')")
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Void> deletarPapel(@PathVariable Long id) {
        papelService.deletarPapel(id);
        return ResponseEntity.noContent().build();
    }


}
