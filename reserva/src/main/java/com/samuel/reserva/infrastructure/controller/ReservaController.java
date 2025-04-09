package com.samuel.reserva.infrastructure.controller;

import com.samuel.reserva.application.service.ReservaService;
import com.samuel.reserva.domain.model.Reserva;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.listar();
    }

    @PostMapping
    public Reserva salvar(@RequestBody Reserva reserva) {
        return reservaService.salvar(reserva);
    }
}
