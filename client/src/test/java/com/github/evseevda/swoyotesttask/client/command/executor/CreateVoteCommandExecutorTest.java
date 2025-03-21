package com.github.evseevda.swoyotesttask.client.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
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

class CreateVoteCommandExecutorTest {

    private Channel channel;
    private ChannelFuture channelFuture;
    private UserInputReader userInputReader;
    private CreateVoteCommandExecutor executor;

    @BeforeEach
    void setUp() {
        channel = Mockito.mock(Channel.class);
        channelFuture = Mockito.mock(ChannelFuture.class);
        userInputReader = Mockito.mock(UserInputReader.class);
        executor = new CreateVoteCommandExecutor(channel, userInputReader);

        when(channel.writeAndFlush(anyString())).thenReturn(channelFuture);
    }

    @Test
    void givenCommand_WhenLogicIsCalled_ThenSendCreateVoteRequest() throws Exception {
        // arrange
        Command command = new Command("create vote", Map.of("t", "topic"));
        when(userInputReader.readString("Название голосования:")).thenReturn("Vote Name");
        when(userInputReader.readString("Тема голосования:")).thenReturn("Vote Description");
        when(userInputReader.readInt("Кол-во ответов:")).thenReturn(2);
        when(userInputReader.readString("Ответ #1:")).thenReturn("Answer 1");
        when(userInputReader.readString("Ответ #2:")).thenReturn("Answer 2");

        // action
        executor.logic(command);

        // assertion
        verify(channel, times(1)).writeAndFlush(anyString());
    }

}