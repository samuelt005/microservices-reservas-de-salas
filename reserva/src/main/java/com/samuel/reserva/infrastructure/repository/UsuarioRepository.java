package com.samuel.reserva.infrastructure.repository;

import com.samuel.reserva.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {}
