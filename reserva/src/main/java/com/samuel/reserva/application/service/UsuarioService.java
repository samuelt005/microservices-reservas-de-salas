package com.samuel.reserva.application.service;

import com.samuel.reserva.domain.model.Usuario;
import com.samuel.reserva.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    @Transactional
    public void saveOrUpdateUsuario(Usuario receivedUsuario) {
        if (receivedUsuario == null || receivedUsuario.getId() == null) {
            return;
        }

        Optional<Usuario> existingUsuarioOpt = usuarioRepository.findById(receivedUsuario.getId());

        Usuario usuarioToSave;
        if (existingUsuarioOpt.isPresent()) {
            usuarioToSave = existingUsuarioOpt.get();
        } else {
            usuarioToSave = new Usuario();
            usuarioToSave.setId(receivedUsuario.getId());
        }
        usuarioToSave.setNome(receivedUsuario.getNome());

        usuarioRepository.save(usuarioToSave);
    }
}
