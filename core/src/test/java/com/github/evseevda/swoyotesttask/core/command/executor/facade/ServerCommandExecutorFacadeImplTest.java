package com.github.evseevda.swoyotesttask.core.command.executor.facade;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ServerCommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class ServerCommandExecutorFacadeImplTest {

    private ServerCommandExecutorFacadeImpl facade;
    private CommandParser commandParser;
    private Map<String, ServerCommandExecutor> commandExecutorMap;
    private ServerCommandExecutor commandExecutor;

    @BeforeEach
    void setUp() {
        commandParser = Mockito.mock(CommandParser.class);
        commandExecutorMap = Mockito.mock(Map.class);
        commandExecutor = Mockito.mock(ServerCommandExecutor.class);

        facade = new ServerCommandExecutorFacadeImpl(commandExecutorMap, commandParser);
    }

    @Test
    void givenValidServerCommand_WhenExecuteIsCalled_ThenReturnExecutionResult() {
        // arrange
        String commandString = "server-test -a=arg1";
        Command command = new Command("server-test", Map.of("a", "arg1"));
        Mockito.when(commandParser.parse(commandString)).thenReturn(command);
        Mockito.when(commandExecutorMap.get("server-test")).thenReturn(commandExecutor);
        Mockito.when(commandExecutor.execute(command)).thenReturn("Server Executed: server-test");

        // action
        String result = facade.execute(commandString);

        // assertion
        Assertions.assertEquals("Server Executed: server-test", result);
        Mockito.verify(commandParser).parse(commandString);
        Mockito.verify(commandExecutorMap).get("server-test");
        Mockito.verify(commandExecutor).execute(command);
    }

}