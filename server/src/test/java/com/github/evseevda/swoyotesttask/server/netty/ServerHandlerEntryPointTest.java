package com.github.evseevda.swoyotesttask.server.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.server.netty.handler.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.Mockito.*;

class ServerHandlerEntryPointTest {

    private Map<RequestAction, RequestHandler> requestHandlerMap;
    private ObjectMapper objectMapper;
    private ChannelHandlerContext channelHandlerContext;
    private ServerHandlerEntryPoint handler;

    @BeforeEach
    void setUp() {
        requestHandlerMap = Mockito.mock(Map.class);
        objectMapper = new ObjectMapper();
        channelHandlerContext = Mockito.mock(ChannelHandlerContext.class);
        handler = new ServerHandlerEntryPoint(requestHandlerMap);
    }

    @Test
    void givenValidRequest_WhenChannelRead0IsCalled_ThenHandleRequest() throws Exception {
        // arrange
        ServerRequest request = new ServerRequest();
        request.setAction(RequestAction.CREATE_VOTE);
        String jsonRequest = objectMapper.writeValueAsString(request);

        RequestHandler requestHandler = Mockito.mock(RequestHandler.class);
        when(requestHandlerMap.get(RequestAction.CREATE_VOTE)).thenReturn(requestHandler);

        // action
        handler.channelRead0(channelHandlerContext, jsonRequest);

        // assertion
        verify(requestHandler, times(1)).handle(request, channelHandlerContext);
    }

    @Test
    void givenInvalidJson_WhenChannelRead0IsCalled_ThenSendErrorResponse() {
        // arrange
        String invalidJson = "invalid json";

        // action & assertion
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> handler.channelRead0(channelHandlerContext, invalidJson));
    }

    @Test
    void givenException_WhenExceptionCaughtIsCalled_ThenCloseContext() throws Exception {
        // arrange
        Throwable cause = new RuntimeException("Test exception");

        // action
        handler.exceptionCaught(channelHandlerContext, cause);

        // assertion
        verify(channelHandlerContext, times(1)).close();
    }

}