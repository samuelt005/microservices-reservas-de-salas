package com.samuel.reserva.infrastructure.repository;

import com.samuel.reserva.domain.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {}
