package com.empresa.vinhos.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProdutoRecomendadoDTO(
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
}