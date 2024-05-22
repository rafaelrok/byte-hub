package br.com.rafaelvieira.bytehub.domain.exception;

public class TakenException extends BusinessException {
    public TakenException() {
        super("has already been taken");
    }
}
