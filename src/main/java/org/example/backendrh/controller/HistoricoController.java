package org.example.backendrh.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.example.backendrh.model.HistoricoAlteracao;
import org.example.backendrh.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/historico")
public class HistoricoController {

    @Autowired
    private HistoricoService service;

    @GetMapping
    public List<HistoricoAlteracao> listar() throws Exception {
        return service.listar();
    }
}
