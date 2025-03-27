package com.samuel.reserva.application.service;

import com.samuel.reserva.domain.model.Reserva;
import com.samuel.reserva.infrastructure.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {
    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    public Reserva salvar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }
}
