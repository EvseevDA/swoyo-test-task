package com.github.evseevda.swoyotesttask.server.input.handler;

import com.github.evseevda.swoyotesttask.core.command.executor.facade.ServerCommandExecutorFacade;
import com.github.evseevda.swoyotesttask.server.netty.server.config.ServerExecutionState;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class ServerCommandHandlerImplTest {

    private ServerCommandExecutorFacade serverCommandExecutorFacade;
    private ServerExecutionState serverExecutionState;
    private Channel serverChannel;
    private ChannelFuture channelFuture;
    private ServerCommandHandlerImpl handler;

    @BeforeEach
    void setUp() {
        serverCommandExecutorFacade = Mockito.mock(ServerCommandExecutorFacade.class);
        serverExecutionState = Mockito.mock(ServerExecutionState.class);
        serverChannel = Mockito.mock(Channel.class);
        channelFuture = Mockito.mock(ChannelFuture.class);
        handler = new ServerCommandHandlerImpl(serverCommandExecutorFacade, serverExecutionState);
    }

    @Test
    void givenExitCommand_WhenHandleIsCalled_ThenSetStoppedAndCloseChannel() {
        // arrange
        String command = "exit";
        when(serverChannel.close()).thenReturn(channelFuture);

        // action
        handler.handle(command, serverChannel);

        // assertion
        verify(serverExecutionState, times(1)).setStopped(true);
        verify(serverChannel, times(1)).close();
    }

    @Test
    void givenNonExitCommand_WhenHandleIsCalled_ThenExecuteCommand() {
        // arrange
        String command = "some command";

        // action
        handler.handle(command, serverChannel);

        // assertion
        verify(serverCommandExecutorFacade, times(1)).execute(command);
        verify(serverExecutionState, never()).setStopped(anyBoolean());
        verify(serverChannel, never()).close();
    }

}