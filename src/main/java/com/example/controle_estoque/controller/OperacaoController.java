package com.example.controle_estoque.controller;

import com.example.controle_estoque.entity.Operacao;
import com.example.controle_estoque.service.OperacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class OperacaoController {
    private final OperacaoService operacaoService;

    @Autowired
    public OperacaoController(OperacaoService operacaoService) {
        this.operacaoService = operacaoService;
    }

    @PreAuthorize("hasAuthority('atualizar_estoque')")
    @PostMapping("/entrada")
    public ResponseEntity<?> registrarEntrada(@RequestParam Long produtoId, @RequestParam Integer quantidade) {
        operacaoService.registrarEntrada(produtoId, quantidade);
        return ResponseEntity.ok("Entrada registrada.");
    }

    @PreAuthorize("hasAuthority('atualizar_estoque')")
    @PostMapping("/saida")
    public ResponseEntity<?> registrarSaida(@RequestParam Long produtoId, @RequestParam Integer quantidade) {
        operacaoService.registrarSaida(produtoId, quantidade);
        return ResponseEntity.ok("Saida registrada.");
    }

    @PreAuthorize("hasAuthority('consultar_estoque')")
    @GetMapping("/historico")
    public ResponseEntity<List<Operacao>> consultarHistoricoOperacoes(@RequestParam(required = false) Long lojaId,
                                                                      @RequestParam String dataInicio,
                                                                      @RequestParam String dataFim) {
        List<Operacao> operacoes;

        if (lojaId != null) {
            operacoes = operacaoService.historicoLoja(lojaId, dataInicio, dataFim);
        } else {
            operacoes = operacaoService.historico(dataInicio, dataFim);
        }

        return ResponseEntity.ok(operacoes);
    }
}
