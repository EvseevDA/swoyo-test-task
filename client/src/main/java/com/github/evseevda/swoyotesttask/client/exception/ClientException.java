package com.github.evseevda.swoyotesttask.client.exception;

import com.github.evseevda.swoyotesttask.core.domain.exception.ApplicationException;

public class ClientException extends ApplicationException {

    public ClientException() {
        super();
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
