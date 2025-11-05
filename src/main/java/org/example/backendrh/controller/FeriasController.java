package org.example.backendrh.controller;

import org.example.backendrh.model.Ferias;
import org.example.backendrh.service.FeriasService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ferias")
@CrossOrigin(origins = "http://localhost:5173") // ajuste se usar outra porta
public class FeriasController {

    private final FeriasService service;

    public FeriasController(FeriasService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ferias> listar() throws Exception {
        return service.listar();
    }

    @PostMapping
    public Ferias criar(@RequestBody Ferias ferias) throws Exception {
        return service.criar(ferias);
    }

    @PutMapping("/{id}")
    public Ferias atualizar(@PathVariable String id, @RequestBody Ferias ferias) throws Exception {
        return service.atualizar(id, ferias);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable String id) throws Exception {
        return service.deletar(id);
    }
}
