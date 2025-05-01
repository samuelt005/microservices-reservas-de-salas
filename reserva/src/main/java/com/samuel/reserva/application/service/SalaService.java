package com.samuel.reserva.application.service;

import com.samuel.reserva.domain.model.Sala;
import com.samuel.reserva.infrastructure.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaService {
    private final SalaRepository salaRepository;


    @Transactional
    public void saveOrUpdateSala(Sala receivedSala) {
        if (receivedSala == null || receivedSala.getId() == null) {
            return;
        }

        Optional<Sala> existingSalaOpt = salaRepository.findById(receivedSala.getId());

        Sala salaToSave;
        if (existingSalaOpt.isPresent()) {
            salaToSave = existingSalaOpt.get();
        } else {
            salaToSave = new Sala();
            salaToSave.setId(receivedSala.getId());
        }
        salaToSave.setNome(receivedSala.getNome());

        salaRepository.save(salaToSave);
    }
}
