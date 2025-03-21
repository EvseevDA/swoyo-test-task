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

class CreateVoteResponseHandlerTest {

    private MessageWriter messageWriter;
    private CreateVoteResponseHandler handler;

    @BeforeEach
    void setUp() {
        messageWriter = Mockito.mock(MessageWriter.class);
        handler = new CreateVoteResponseHandler(messageWriter);
    }


    @Test
    void givenResponse_WhenHandleIsCalled_ThenWriteStatusAndBody() {
        // arrange
        ServerResponse response = new ServerResponse();
        response.setStatus(ResponseStatus.OK);
        response.setBody("Vote created successfully");

        // action
        handler.handle(response);

        // assertion
        verify(messageWriter).writeln("Status: " + ResponseStatus.OK.name());
        verify(messageWriter).writeln("Vote created successfully");
    }

    @Test
    void givenHandler_WhenActionIsCalled_ThenReturnCreateVoteAction() {
        // action
        RequestAction action = handler.action();

        // assertion
        assert action == RequestAction.CREATE_VOTE;
    }

}