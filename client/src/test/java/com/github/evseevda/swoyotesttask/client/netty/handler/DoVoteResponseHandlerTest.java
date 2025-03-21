package com.github.evseevda.swoyotesttask.client.netty.handler;

import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.response.ResponseStatus;
import com.github.evseevda.swoyotesttask.core.messaging.response.ServerResponse;
import com.github.evseevda.swoyotesttask.core.ui.output.MessageWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class DoVoteResponseHandlerTest {

    private MessageWriter messageWriter;
    private DoVoteResponseHandler handler;

    @BeforeEach
    void setUp() {
        messageWriter = Mockito.mock(MessageWriter.class);
        handler = new DoVoteResponseHandler(messageWriter);
    }

    @Test
    void givenResponse_WhenHandleIsCalled_ThenWriteStatus() {
        // arrange
        ServerResponse response = new ServerResponse();
        response.setStatus(ResponseStatus.OK);

        // action
        handler.handle(response);

        // assertion
        verify(messageWriter).writeln("Status: " + ResponseStatus.OK);
    }

    @Test
    void givenHandler_WhenActionIsCalled_ThenReturnDoVoteAction() {
        // action
        RequestAction action = handler.action();

        // assertion
        assert action == RequestAction.DO_VOTE;
    }

}