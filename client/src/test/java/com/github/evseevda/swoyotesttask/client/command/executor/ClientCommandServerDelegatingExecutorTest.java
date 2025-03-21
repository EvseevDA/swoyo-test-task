package com.github.evseevda.swoyotesttask.client.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ClientCommandServerDelegatingExecutorTest {

    private Channel channel;
    private ChannelFuture channelFuture;
    private ClientCommandServerDelegatingExecutor executor;

    @BeforeEach
    void setUp() {
        channel = Mockito.mock(Channel.class);
        channelFuture = Mockito.mock(ChannelFuture.class);
        when(channel.writeAndFlush(any())).thenReturn(channelFuture);
        executor = new ClientCommandServerDelegatingExecutor(channel) {
            @Override
            public String command() {
                return "any";
            }
        };
    }

    @Test
    void givenCommand_WhenLogicIsCalled_ThenWrapAndSendCommand() throws Exception {
        // arrange
        Command command = new Command("test", Map.of("arg", "value"));

        // action
        executor.logic(command);

        // assertion
        verify(channel, times(1)).writeAndFlush(anyString());
    }

}