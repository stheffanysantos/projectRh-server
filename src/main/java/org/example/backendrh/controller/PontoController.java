package org.example.backendrh.controller;

import org.example.backendrh.model.Ponto;
import org.example.backendrh.service.PontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pontos")
public class PontoController {

    @Autowired
    private PontoService pontoService;

    // Registrar ponto
    @PostMapping("/registrar/{funcionarioId}")
    public ResponseEntity<Ponto> registrar(@PathVariable String funcionarioId) {
        try {
            return ResponseEntity.ok(pontoService.registrarPonto(funcionarioId));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Listar pontos por funcionário
    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<Ponto>> listarPorFuncionario(@PathVariable String funcionarioId) {
        try {
            return ResponseEntity.ok(pontoService.listarPorFuncionario(funcionarioId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of());
        }
    }

    // Listar todos os registros
    @GetMapping
    public ResponseEntity<List<Ponto>> listarTodos() {
        try {
            return ResponseEntity.ok(pontoService.listarTodos());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of());
        }
    }

    // Contador de pontos por funcionário (para cards)
    @GetMapping("/funcionario/{funcionarioId}/contagem")
    public ResponseEntity<Integer> contarPontos(@PathVariable String funcionarioId) {
        try {
            return ResponseEntity.ok(pontoService.contarPorFuncionario(funcionarioId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(0);
        }
    }
}
