package com.github.evseevda.swoyotesttask.core.command.executor;

public enum CommandExecutionResult {

    OK(1),
    ERROR(0);

    CommandExecutionResult(int status) {
        this.status = status;
    }

    private final int status;

    public int statusCode() {
        return status;
    }

}
