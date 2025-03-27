package com.samuel.sala.infrastructure.controller;

import com.samuel.sala.application.service.SalaService;
import com.samuel.sala.domain.model.Sala;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {
    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public List<Sala> listar() {
        return salaService.listar();
    }

    @PostMapping
    public Sala salvar(@RequestBody Sala sala) {
        return salaService.salvar(sala);
    }
}
