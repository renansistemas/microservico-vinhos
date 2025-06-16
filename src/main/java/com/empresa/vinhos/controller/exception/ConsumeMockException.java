package com.empresa.vinhos.controller.exception;

public class ConsumeMockException extends RuntimeException {

    public ConsumeMockException() {
        super("Erro ao consumir o mock.");
    }

    public ConsumeMockException(String message) {
        super(message);
    }
}
