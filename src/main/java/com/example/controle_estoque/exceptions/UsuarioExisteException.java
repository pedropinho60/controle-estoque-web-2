package com.example.controle_estoque.exceptions;

public class UsuarioExisteException extends RuntimeException {
    public UsuarioExisteException(String message) {
        super(message);
    }
}
