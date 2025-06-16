package com.empresa.vinhos.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ClienteFielResponseDTO(
        @JsonProperty("nome_cliente")
        String nomeCliente,

        @JsonProperty("cpf_cliente")
        String cpfCliente,

        @JsonProperty("total_compras")
        Integer totalCompras,

        @JsonProperty("valor_total_gasto")
        BigDecimal valorTotalGasto

//        @JsonProperty("produtos_diferentes")
//        Integer produtosDiferentes
) {
}
