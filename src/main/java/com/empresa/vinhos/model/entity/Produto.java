package com.empresa.vinhos.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record Produto(
        @NotNull(message = "Código do produto é obrigatório")
        @JsonProperty("codigo")
        Integer codigo,

        @JsonProperty("tipo_vinho")
        String tipoVinho,

        @JsonProperty("preco")
        BigDecimal preco,

        @JsonProperty("safra")
        String safra,

        @JsonProperty("ano_compra")
        Integer anoCompra
) {
    public static Produto of(Integer codigo, String tipoVinho, double preco,
                             String safra, Integer anoCompra) {
        return new Produto(codigo, tipoVinho, BigDecimal.valueOf(preco), safra, anoCompra);
    }
}
