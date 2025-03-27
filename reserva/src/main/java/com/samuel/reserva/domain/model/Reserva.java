package com.samuel.reserva.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHora;
    private Long salaId;
    private Long usuarioId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id) && Objects.equals(dataHora, reserva.dataHora) && Objects.equals(salaId, reserva.salaId) && Objects.equals(usuarioId, reserva.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataHora, salaId, usuarioId);
    }
}
