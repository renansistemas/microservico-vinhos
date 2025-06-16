package com.empresa.vinhos.controller.exception;

public class InvalidCodeItemException extends RuntimeException {

    public InvalidCodeItemException() {
        super("Código do item inválido.");
    }

    public InvalidCodeItemException(String message) {
        super(message);
    }
}
