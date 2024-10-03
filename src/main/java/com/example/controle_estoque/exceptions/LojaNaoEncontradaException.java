package com.example.controle_estoque.exceptions;

public class LojaNaoEncontradaException extends RuntimeException {
    public LojaNaoEncontradaException(String message) {
        super(message);
    }
}
