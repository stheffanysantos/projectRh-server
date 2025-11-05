package org.example.backendrh.controller;

import org.example.backendrh.model.Funcionario;
import org.example.backendrh.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @PostMapping
    public ResponseEntity<Funcionario> adicionar(@RequestBody Funcionario funcionario) throws Exception {
        return ResponseEntity.ok(service.salvar(funcionario));
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listar() throws Exception {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscar(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) throws Exception {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Opcional: rota de atualização
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizar(@PathVariable String id, @RequestBody Funcionario funcionario) throws Exception {
        funcionario.setId(id);
        return ResponseEntity.ok(service.atualizar(funcionario));
    }
}
