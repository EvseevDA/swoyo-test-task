package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.command.executor.facade.ClientCommandExecutorFacade;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class ExecuteCommandRequestHandlerTest {

    private ClientCommandExecutorFacade clientCommandExecutorFacade;
    private ObjectMapper objectMapper;
    private ChannelHandlerContext context;
    private ExecuteCommandRequestHandler handler;

    @BeforeEach
    void setUp() {
        clientCommandExecutorFacade = Mockito.mock(ClientCommandExecutorFacade.class);
        objectMapper = Mockito.mock(ObjectMapper.class);
        context = Mockito.mock(ChannelHandlerContext.class);
        handler = new ExecuteCommandRequestHandler(clientCommandExecutorFacade);
    }

    @Test
    void givenValidRequest_WhenHandleIsCalled_ThenWriteCommandOutput() throws JsonProcessingException {
        // arrange
        ServerRequest request = new ServerRequest();
        request.setBody("command");

        when(clientCommandExecutorFacade.execute("command")).thenReturn("output");

        // action
        handler.handle(request, context);

        // assertion
        verify(context, times(1)).writeAndFlush(anyString());
    }

    @Test
    void givenError_WhenHandleIsCalled_ThenWriteErrorResponse() throws JsonProcessingException {
        // arrange
        ServerRequest request = new ServerRequest();
        request.setBody("command");

        when(clientCommandExecutorFacade.execute("command")).thenThrow(RuntimeException.class);

        // action
        handler.handle(request, context);

        // assertion
        verify(context, times(1)).writeAndFlush(anyString());
    }

}