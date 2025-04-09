package com.samuel.usuario.infrastructure.controller;

import com.samuel.usuario.application.service.UsuarioService;
import com.samuel.usuario.domain.model.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    @PutMapping
    public Usuario editar(@RequestBody Usuario usuario) {
        return usuarioService.editar(usuario);
    }
}
