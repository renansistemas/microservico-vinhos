package com.empresa.vinhos.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecomendacaoResponseDTO(
        @JsonProperty("cpf_cliente")
        String cpfCliente,

        @JsonProperty("tipo_vinho_preferido")
        String tipoVinhoPreferido,

        @JsonProperty("produto_recomendado")
        ProdutoRecomendadoDTO produtoRecomendado
) {
}