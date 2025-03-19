package com.github.evseevda.swoyotesttask.server.exception;

import com.github.evseevda.swoyotesttask.core.domain.exception.ApplicationException;

public class ServerException extends ApplicationException {

    public ServerException() {
        super();
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
