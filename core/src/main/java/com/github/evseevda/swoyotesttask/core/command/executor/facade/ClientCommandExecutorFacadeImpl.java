package com.github.evseevda.swoyotesttask.core.command.executor.facade;

import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParser;

import java.util.Map;

public class ClientCommandExecutorFacadeImpl
        extends AbstractCommandExecutorFacade
        implements ClientCommandExecutorFacade {

    public ClientCommandExecutorFacadeImpl(Map<String, ClientCommandExecutor> commandExecutorMap, CommandParser commandParser) {
        super(commandExecutorMap, commandParser);
    }

}
