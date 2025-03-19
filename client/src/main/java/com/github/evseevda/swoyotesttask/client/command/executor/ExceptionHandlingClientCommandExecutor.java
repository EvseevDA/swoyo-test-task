package com.github.evseevda.swoyotesttask.client.command.executor;

import com.github.evseevda.swoyotesttask.client.exception.ClientException;
import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;

public abstract class ExceptionHandlingClientCommandExecutor implements ClientCommandExecutor {

    @Override
    public String execute(Command command) {
        try {
            return logic(command);
        } catch (Exception e) {
            throw new ClientException("Client exception occurred.", e);
        }
    }

    protected abstract String logic(Command command) throws Exception;
}
