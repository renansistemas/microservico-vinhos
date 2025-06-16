package com.empresa.vinhos.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record CompraResponseDTO(
        @JsonProperty("nome_cliente")
        String nomeCliente,

        @JsonProperty("cpf_cliente")
        String cpfCliente,

        @JsonProperty("codigo_produto")
        Integer codigoProduto,

        @JsonProperty("tipo_vinho")
        String tipoVinho,

        @JsonProperty("preco_unitario")
        BigDecimal precoUnitario,

        @JsonProperty("safra")
        String safra,

        @JsonProperty("ano_compra")
        Integer anoCompra,

        @JsonProperty("quantidade")
        Integer quantidade,

        @JsonProperty("valor_total")
        BigDecimal valorTotal
) {
}