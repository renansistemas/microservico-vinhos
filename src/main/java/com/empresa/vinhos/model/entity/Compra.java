package com.empresa.vinhos.model.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record Compra(
        @NotNull(message = "Cliente é obrigatório")
        Cliente cliente,

        @NotNull(message = "Produto é obrigatório")
        Produto produto,

        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        Integer quantidade
) {
}
