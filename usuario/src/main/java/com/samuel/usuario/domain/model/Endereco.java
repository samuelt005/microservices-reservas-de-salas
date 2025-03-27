package com.samuel.usuario.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class Endereco {
    @Embedded
    private String telefone;
    @Embedded
    private String rua;
    @Embedded
    private String numero;
    @Embedded
    private String cidade;
    @Embedded
    private String cep;

    public void setRua(String rua) {
        if (rua == null || rua.trim().length() < 3) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 3 caracteres.");
        }
        this.rua = rua;
    }
}
