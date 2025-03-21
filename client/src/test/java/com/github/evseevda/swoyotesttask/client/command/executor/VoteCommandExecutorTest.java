package com.github.evseevda.swoyotesttask.client.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.ui.input.UserInputReader;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VoteCommandExecutorTest {

    private Channel channel;
    private ChannelFuture channelFuture;
    private UserInputReader userInputReader;
    private ClientCommandExecutor viewCommandExecutor;
    private VoteCommandExecutor executor;

    @BeforeEach
    void setUp() {
        channel = Mockito.mock(Channel.class);
        channelFuture = Mockito.mock(ChannelFuture.class);
        userInputReader = Mockito.mock(UserInputReader.class);
        viewCommandExecutor = Mockito.mock(ClientCommandExecutor.class);
        executor = new VoteCommandExecutor(channel, viewCommandExecutor, userInputReader);

        when(channel.writeAndFlush(anyString())).thenReturn(channelFuture);
    }

    @Test
    void givenCommand_WhenLogicIsCalled_ThenSendVoteRequest() throws Exception {
        // arrange
        Command command = new Command("vote", Map.of("t", "topic", "v", "vote"));
        when(userInputReader.readInt("Введите номер ответа:")).thenReturn(1);

        // action
        executor.logic(command);

        // assertion
        verify(viewCommandExecutor, times(1)).execute(any(Command.class));
        verify(channel, times(1)).writeAndFlush(anyString());
    }

}