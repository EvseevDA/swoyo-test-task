package com.github.evseevda.swoyotesttask.client.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.client.netty.handler.ResponseHandler;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.response.ServerResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ClientHandlerEntryPoint extends SimpleChannelInboundHandler<String> {

    private final Map<RequestAction, ResponseHandler> responseHandlerMap;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        ServerResponse serverResponse = objectMapper.readValue(msg, ServerResponse.class);
        responseHandlerMap.get(serverResponse.getAction()).handle(serverResponse);
    }

}
