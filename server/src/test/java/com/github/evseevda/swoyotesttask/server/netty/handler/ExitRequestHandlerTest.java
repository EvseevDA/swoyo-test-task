package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ExitRequestHandlerTest {

    private ChannelHandlerContext context;
    private ExitRequestHandler handler;

    @BeforeEach
    void setUp() {
        context = Mockito.mock(ChannelHandlerContext.class);
        handler = new ExitRequestHandler();
    }

    @Test
    void givenRequest_WhenHandleIsCalled_ThenCloseContext() {
        // arrange
        ServerRequest request = new ServerRequest();

        // action
        handler.handle(request, context);

        // assertion
        verify(context, times(1)).close();
    }

}