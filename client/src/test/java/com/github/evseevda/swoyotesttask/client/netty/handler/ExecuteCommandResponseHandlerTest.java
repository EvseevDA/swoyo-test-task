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

class ExecuteCommandResponseHandlerTest {

    private MessageWriter messageWriter;
    private ExecuteCommandResponseHandler handler;

    @BeforeEach
    void setUp() {
        messageWriter = Mockito.mock(MessageWriter.class);
        handler = new ExecuteCommandResponseHandler(messageWriter);
    }

    @Test
    void givenResponse_WhenHandleIsCalled_ThenWriteStatusAndBody() {
        // arrange
        ServerResponse response = new ServerResponse();
        response.setStatus(ResponseStatus.OK);
        response.setBody("Command executed successfully");

        // action
        handler.handle(response);

        // assertion
        verify(messageWriter).writeln("Status: " + ResponseStatus.OK);
        verify(messageWriter).writeln("Command executed successfully");
    }

    @Test
    void givenHandler_WhenActionIsCalled_ThenReturnExecuteCommandAction() {
        // action
        RequestAction action = handler.action();

        // assertion
        assert action == RequestAction.EXECUTE_COMMAND;
    }

}