package com.samuel.sala.application.service;

import com.samuel.sala.domain.model.Sala;
import com.samuel.sala.infrastructure.repository.SalaRepository;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaService {
    private final SalaRepository salaRepository;
    private final RabbitTemplate rabbitTemplate;
    private final String salaExchangeName;

    public SalaService(
            SalaRepository salaRepository,
            RabbitTemplate rabbitTemplate,
            @Value("${sala.rabbitmq.exchange}") String salaExchangeName
    ) {
        this.salaRepository = salaRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.salaExchangeName = salaExchangeName;
    }

    public List<Sala> listar() {
        return salaRepository.findAll();
    }

    @Transactional
    public Sala salvar(Sala sala) {
        Sala newSala = salaRepository.save(sala);
        System.out.println("Sala saved successfully with ID:");
        System.out.println(newSala.getId());

        try {
            rabbitTemplate.convertAndSend(salaExchangeName, "", newSala);
            System.out.println("Sala event published to exchange for Sala ID:");
            System.out.println(newSala.getId());
        } catch (AmqpException e) {
            System.out.println("Failed to publish Sala event. Error:");
            System.out.println(e.getMessage());
        }

        return newSala;
    }
}
