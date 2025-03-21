package com.github.evseevda.swoyotesttask.client.input.handler;

import com.github.evseevda.swoyotesttask.core.command.executor.facade.ClientCommandExecutorFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientCommandHandlerImplTest {

    private ClientCommandHandlerImpl clientCommandHandler;
    private ClientCommandExecutorFacade clientCommandExecutorFacade;

    @BeforeEach
    void setUp() {
        clientCommandExecutorFacade = Mockito.mock(ClientCommandExecutorFacade.class);
        clientCommandHandler = new ClientCommandHandlerImpl(clientCommandExecutorFacade);
    }

    @Test
    void givenValidCommand_WhenHandleIsCalled_ThenExecuteCommand() {
        // arrange
        String command = "test -a=arg1";
        String expectedResult = "Executed: test";
        when(clientCommandExecutorFacade.execute(command)).thenReturn(expectedResult);

        // action
        clientCommandHandler.handle(command);

        // assertion
        verify(clientCommandExecutorFacade).execute(command);
    }

    @Test
    void givenNullCommand_WhenHandleIsCalled_ThenPassNullToExecutor() {
        // arrange
        String command = null;
        String expectedResult = "Executed: null";
        when(clientCommandExecutorFacade.execute(command)).thenReturn(expectedResult);

        // action
        clientCommandHandler.handle(command);

        // assertion
        verify(clientCommandExecutorFacade).execute(command);
    }

    @Test
    void givenEmptyCommand_WhenHandleIsCalled_ThenPassEmptyCommandToExecutor() {
        // arrange
        String command = "";
        String expectedResult = "Executed: empty";
        when(clientCommandExecutorFacade.execute(command)).thenReturn(expectedResult);

        // action
        clientCommandHandler.handle(command);

        // assertion
        verify(clientCommandExecutorFacade).execute(command);
    }

}