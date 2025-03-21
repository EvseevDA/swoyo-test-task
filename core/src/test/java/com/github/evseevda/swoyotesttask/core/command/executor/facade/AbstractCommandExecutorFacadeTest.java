package com.github.evseevda.swoyotesttask.core.command.executor.facade;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class AbstractCommandExecutorFacadeTest {

    private AbstractCommandExecutorFacade facade;
    private CommandParser commandParser;
    private Map<String, CommandExecutor> commandExecutorMap;
    private CommandExecutor commandExecutor;

    @BeforeEach
    void setUp() {
        commandParser = Mockito.mock(CommandParser.class);
        commandExecutorMap = Mockito.mock(Map.class);
        commandExecutor = Mockito.mock(CommandExecutor.class);

        facade = new AbstractCommandExecutorFacade(commandExecutorMap, commandParser) { };
    }

    @Test
    void givenValidCommand_WhenExecuteIsCalled_ThenReturnExecutionResult() {
        // arrange
        String commandString = "test -a=arg1";
        Command command = new Command("test", Map.of("a", "arg1"));
        Mockito.when(commandParser.parse(commandString)).thenReturn(command);
        Mockito.when(commandExecutorMap.get("test")).thenReturn(commandExecutor);
        Mockito.when(commandExecutor.execute(command)).thenReturn("Executed: test");

        // action
        String result = facade.execute(commandString);

        // assertion
        Assertions.assertEquals("Executed: test", result);
        Mockito.verify(commandParser).parse(commandString);
        Mockito.verify(commandExecutorMap).get("test");
        Mockito.verify(commandExecutor).execute(command);
    }

    @Test
    void givenUnknownCommand_WhenExecuteIsCalled_ThenThrowException() {
        // arrange
        String commandString = "unknown -a=arg1";
        Command command = new Command("unknown", Map.of("a", "arg1"));
        Mockito.when(commandParser.parse(commandString)).thenReturn(command);
        Mockito.when(commandExecutorMap.get("unknown")).thenReturn(null);

        // action & assertion
        Assertions.assertThrows(NullPointerException.class, () -> facade.execute(commandString));
        Mockito.verify(commandParser).parse(commandString);
        Mockito.verify(commandExecutorMap).get("unknown");
    }

}