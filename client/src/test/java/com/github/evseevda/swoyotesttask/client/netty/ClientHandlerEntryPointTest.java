package com.github.evseevda.swoyotesttask.client.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.client.netty.handler.ResponseHandler;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.response.ResponseStatus;
import com.github.evseevda.swoyotesttask.core.messaging.response.ServerResponse;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.Mockito.*;

class ClientHandlerEntryPointTest {

    private ChannelHandlerContext ctx;
    private Map<RequestAction, ResponseHandler> responseHandlerMap;
    private ClientHandlerEntryPoint handler;
    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        ctx = Mockito.mock(ChannelHandlerContext.class);
        responseHandlerMap = Mockito.mock(Map.class);
        handler = new ClientHandlerEntryPoint(responseHandlerMap);
    }

    @Test
    void givenValidMessage_WhenChannelRead0IsCalled_ThenHandleResponse() throws Exception {
        // arrange
        String jsonMessage = "{\"action\":\"EXECUTE_COMMAND\",\"status\":\"OK\",\"body\":\"Command executed\"}";
        ServerResponse serverResponse = objectMapper.readValue(jsonMessage, ServerResponse.class);
        ResponseHandler responseHandler = Mockito.mock(ResponseHandler.class);

        when(responseHandlerMap.get(RequestAction.EXECUTE_COMMAND)).thenReturn(responseHandler);

        // action
        handler.channelRead0(ctx, jsonMessage);

        // assertion
        verify(responseHandlerMap, times(1)).get(RequestAction.EXECUTE_COMMAND);
        verify(responseHandler, times(1)).handle(serverResponse);
    }

    @Test
    void givenInvalidJson_WhenChannelRead0IsCalled_ThenThrowException() {
        // arrange
        String invalidJsonMessage = "invalid json";

        // action & assertion
        Assertions.assertThrows(Exception.class, () -> handler.channelRead0(ctx, invalidJsonMessage));
    }

}