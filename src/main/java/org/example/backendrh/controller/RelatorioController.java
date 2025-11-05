package org.example.backendrh.controller;
import org.example.backendrh.model.Funcionario;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {
    @GetMapping
    public List<Funcionario> relatorio(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String departamento
    ) {
        // Filtre conforme os params
        return null;
    }
}
