package br.com.fiap.bairroemdia.atendimentos.exception;

public class ConflitoDeEstado extends RuntimeException {
    public ConflitoDeEstado(String mensagem) { super(mensagem); }
}
