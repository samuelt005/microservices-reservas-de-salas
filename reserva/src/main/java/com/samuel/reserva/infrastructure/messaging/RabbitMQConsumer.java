package com.samuel.reserva.infrastructure.messaging;

import com.samuel.reserva.application.service.SalaService;
import com.samuel.reserva.application.service.UsuarioService;
import com.samuel.reserva.domain.model.Sala;
import com.samuel.reserva.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    private final SalaService salaService;
    private final UsuarioService usuarioService;

    @RabbitListener(queues = "${sala.rabbitmq.queue}")
    public void receiveSalaMessage(@Payload Sala receivedSala) {
        try {
            salaService.saveOrUpdateSala(receivedSala);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Error processing Sala message", e);
        }
    }

    @RabbitListener(queues = "${usuario.rabbitmq.queue}")
    public void receiveUsuarioMessage(@Payload Usuario receivedUsuario) {
        try {
            usuarioService.saveOrUpdateUsuario(receivedUsuario);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Error processing Usuario message", e);
        }
    }
}
