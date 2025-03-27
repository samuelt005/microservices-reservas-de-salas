package com.samuel.reserva.infrastructure.repository;

import com.samuel.reserva.domain.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {}
