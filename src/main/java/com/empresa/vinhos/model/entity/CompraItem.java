package com.empresa.vinhos.model.entity;


import com.empresa.vinhos.controller.exception.InvalidCodeItemException;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CompraItem(
        @JsonProperty("codigo")
        String codigo,

        @JsonProperty("quantidade")
        Integer quantidade
) {
    public Integer getCodigoAsInteger() {
        try {
            return Integer.parseInt(codigo);
        } catch (NumberFormatException e) {
            throw new InvalidCodeItemException();
        }
    }
}