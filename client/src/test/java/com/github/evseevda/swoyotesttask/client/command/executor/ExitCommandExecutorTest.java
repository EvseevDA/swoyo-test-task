package com.github.evseevda.swoyotesttask.client.command.executor;

import com.github.evseevda.swoyotesttask.client.netty.config.ClientExecutionState;
import com.github.evseevda.swoyotesttask.core.command.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ExitCommandExecutorTest {

    private Channel channel;
    private ChannelFuture channelFuture;
    private ClientExecutionState clientExecutionState;
    private ExitCommandExecutor executor;

    @BeforeEach
    void setUp() {
        channel = Mockito.mock(Channel.class);
        channelFuture = Mockito.mock(ChannelFuture.class);
        clientExecutionState = Mockito.mock(ClientExecutionState.class);
        executor = new ExitCommandExecutor(clientExecutionState, channel);

        when(channel.writeAndFlush(anyString())).thenReturn(channelFuture);
    }

    @Test
    void givenCommand_WhenExecuteIsCalled_ThenSetStoppedAndSendExitRequest() {
        // arrange
        Command command = new Command("exit", Map.of());

        // action
        executor.execute(command);

        // assertion
        verify(clientExecutionState, times(1)).setStopped(true);
        verify(channel, times(1)).writeAndFlush(anyString());
    }

}