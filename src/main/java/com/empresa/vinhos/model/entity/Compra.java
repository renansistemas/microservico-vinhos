package com.empresa.vinhos.model.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Compra(
        @NotNull(message = "Cliente é obrigatório")
        Cliente cliente,

        @NotNull(message = "Produto é obrigatório")
        Produto produto,

        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "Quantidade deve ser maior que zero")
        Integer quantidade
) {
    public BigDecimal getValorTotal() {
        return produto.preco()
                .multiply(BigDecimal.valueOf(quantidade))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
