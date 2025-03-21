package com.github.evseevda.swoyotesttask.core.command.executor.facade;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

class ClientCommandExecutorFacadeImplTest {

    private ClientCommandExecutorFacadeImpl facade;
    private CommandParser commandParser;
    private Map<String, ClientCommandExecutor> commandExecutorMap;
    private ClientCommandExecutor commandExecutor;

    @BeforeEach
    void setUp() {
        commandParser = Mockito.mock(CommandParser.class);
        commandExecutorMap = Mockito.mock(Map.class);
        commandExecutor = Mockito.mock(ClientCommandExecutor.class);

        facade = new ClientCommandExecutorFacadeImpl(commandExecutorMap, commandParser);
    }

    @Test
    void givenValidClientCommand_WhenExecuteIsCalled_ThenReturnExecutionResult() {
        // arrange
        String commandString = "client-test -a=arg1";
        Command command = new Command("client-test", Map.of("a", "arg1"));
        Mockito.when(commandParser.parse(commandString)).thenReturn(command);
        Mockito.when(commandExecutorMap.get("client-test")).thenReturn(commandExecutor);
        Mockito.when(commandExecutor.execute(command)).thenReturn("Client Executed: client-test");

        // action
        String result = facade.execute(commandString);

        // assertion
        Assertions.assertEquals("Client Executed: client-test", result);
        Mockito.verify(commandParser).parse(commandString);
        Mockito.verify(commandExecutorMap).get("client-test");
        Mockito.verify(commandExecutor).execute(command);
    }

}