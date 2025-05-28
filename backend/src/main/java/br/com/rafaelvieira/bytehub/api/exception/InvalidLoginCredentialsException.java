package br.com.rafaelvieira.bytehub.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLoginCredentialsException extends ResponseStatusException {

    public InvalidLoginCredentialsException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }
}
