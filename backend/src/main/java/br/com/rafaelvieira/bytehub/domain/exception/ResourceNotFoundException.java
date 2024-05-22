package br.com.rafaelvieira.bytehub.domain.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException() {
        super("not found");
    }
}
