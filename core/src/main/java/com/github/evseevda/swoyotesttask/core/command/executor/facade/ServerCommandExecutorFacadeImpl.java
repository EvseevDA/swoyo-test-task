package com.github.evseevda.swoyotesttask.core.command.executor.facade;

import com.github.evseevda.swoyotesttask.core.command.executor.ServerCommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParser;

import java.util.Map;

public class ServerCommandExecutorFacadeImpl
        extends AbstractCommandExecutorFacade
        implements ServerCommandExecutorFacade {


    public ServerCommandExecutorFacadeImpl(Map<String, ServerCommandExecutor> commandExecutorMap, CommandParser commandParser) {
        super(commandExecutorMap, commandParser);
    }

}
