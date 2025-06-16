package com.empresa.vinhos.controller.exception;

public class InitializeApplicationException extends RuntimeException {

    public InitializeApplicationException() {
        super("Erro ao inicializar a aplicação.");
    }

    public InitializeApplicationException(String message) {
        super("Erro ao inicializar a aplicação. Detalhes: " + message);
    }
}
