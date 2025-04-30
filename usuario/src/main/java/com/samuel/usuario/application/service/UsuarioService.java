package com.samuel.usuario.application.service;

import com.samuel.usuario.domain.model.Usuario;
import com.samuel.usuario.infrastructure.repository.UsuarioRepository;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final RabbitTemplate rabbitTemplate;
    private final String usuarioExchangeName;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            RabbitTemplate rabbitTemplate,
            @Value("${usuario.rabbitmq.exchange}") String usuarioExchangeName
    ) {
        this.usuarioRepository = usuarioRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.usuarioExchangeName = usuarioExchangeName;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        Usuario newUsuario = usuarioRepository.save(usuario);
        System.out.println("Usuário saved successfully with ID:");
        System.out.println(newUsuario.getId());

        try {
            rabbitTemplate.convertAndSend(usuarioExchangeName, "", newUsuario);
            System.out.println("Usuário event published to exchange for Usuário ID:");
            System.out.println(newUsuario.getId());
        } catch (AmqpException e) {
            System.out.println("Failed to publish Usuário event. Error:");
            System.out.println(e.getMessage());
        }

        return newUsuario;
    }

    // public Usuario editar(Usuario usuario) {
    //     Usuario usuarioExistente = usuarioRepository.findById(usuario.getId())
    //             .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    //
    //     usuarioExistente.setNome(usuario.getNome());
    //
    //     return usuarioRepository.save(usuarioExistente);
    // }

    // public Optional<Usuario> buscarPorEmail(String email) {
    //     return usuarioRepository.findByEmail(email);
    // }
}
