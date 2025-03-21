package com.github.evseevda.swoyotesttask.server.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.response.ServerResponse;
import com.github.evseevda.swoyotesttask.server.netty.handler.RequestHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class ServerHandlerEntryPoint extends SimpleChannelInboundHandler<String> {

    private final Map<RequestAction, RequestHandler> requestHandlerMap;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String clientAddress = ctx.channel().remoteAddress().toString();
        log.info("Channel opened: {}. Client: {}.", ctx.channel().id(), clientAddress);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientAddress = ctx.channel().remoteAddress().toString();
        log.info("Channel closed: {}. Client: {}.", ctx.channel().id(), clientAddress);
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        ServerRequest request = objectMapper.readValue(s, ServerRequest.class);
        RequestAction requestAction = request.getAction();
        try {
            requestHandlerMap.get(requestAction).handle(request, channelHandlerContext);
        } catch (Throwable e) {
            channelHandlerContext.writeAndFlush(objectMapper.writeValueAsString(ServerResponse.error(requestAction, "Some error occurred.")));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("IO exception occurred.", cause);
        ctx.close();
    }


}
