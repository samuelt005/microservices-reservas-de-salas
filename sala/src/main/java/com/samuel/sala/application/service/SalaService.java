package com.samuel.sala.application.service;

import com.samuel.sala.domain.model.Sala;
import com.samuel.sala.infrastructure.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Sala> listar() {
        return salaRepository.findAll();
    }

    public Sala salvar(Sala sala) {
        return salaRepository.save(sala);
    }
}
