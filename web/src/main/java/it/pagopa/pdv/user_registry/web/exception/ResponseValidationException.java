package it.pagopa.pdv.user_registry.web.exception;

public class ResponseValidationException extends RuntimeException {

    public ResponseValidationException(String message) {
        super(message);
    }

}
