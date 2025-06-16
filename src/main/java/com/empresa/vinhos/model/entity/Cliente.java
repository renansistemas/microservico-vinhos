package com.empresa.vinhos.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record Cliente(
        @NotBlank(message = "Nome é obrigatório")
        @JsonProperty("nome")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
        @JsonProperty("cpf")
        String cpf,

        @JsonProperty("compras")
        List<CompraItem> compras
) {

    public String getCpfFormatado() {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
