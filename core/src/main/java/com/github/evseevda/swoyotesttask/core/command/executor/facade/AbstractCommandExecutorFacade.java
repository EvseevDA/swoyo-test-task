package com.github.evseevda.swoyotesttask.core.command.executor.facade;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParser;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public abstract class AbstractCommandExecutorFacade implements CommandExecutorFacade {

    private final Map<String, ? extends CommandExecutor> commandExecutorMap;
    private final CommandParser commandParser;

    @Override
    public String execute(String command) {
        Command parsedCommand = commandParser.parse(command);
        return commandExecutorMap.get(parsedCommand.getCommand())
                .execute(parsedCommand);
    }

}
