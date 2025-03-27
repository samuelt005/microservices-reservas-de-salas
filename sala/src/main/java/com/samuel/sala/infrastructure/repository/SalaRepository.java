package com.samuel.sala.infrastructure.repository;

import com.samuel.sala.domain.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {}
