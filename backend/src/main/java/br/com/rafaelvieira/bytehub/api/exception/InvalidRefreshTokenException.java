package br.com.rafaelvieira.bytehub.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRefreshTokenException extends ResponseStatusException {

    public InvalidRefreshTokenException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }
}
