package com.github.evseevda.swoyotesttask.server.netty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.response.ResponseStatus;
import com.github.evseevda.swoyotesttask.core.messaging.response.ServerResponse;
import com.github.evseevda.swoyotesttask.server.exception.UserNotAuthenticatedException;
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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        ServerRequest request = objectMapper.readValue(s, ServerRequest.class);
        RequestAction requestAction = request.getAction();
        try {
            requestHandlerMap.get(requestAction).handle(request, channelHandlerContext);
        } catch (UserNotAuthenticatedException e) {
            sendUnauthorizedResponse(channelHandlerContext);
        } catch (Throwable e) {
            channelHandlerContext.writeAndFlush(objectMapper.writeValueAsString(ServerResponse.error(requestAction, "Some error occurred.")));
        }
    }

    private void sendUnauthorizedResponse(ChannelHandlerContext channelHandlerContext) throws JsonProcessingException {
        ServerResponse response = ServerResponse.builder()
                .status(ResponseStatus.UNAUTHORIZED)
                .build();
        String responseString = objectMapper.writeValueAsString(response);
        channelHandlerContext.writeAndFlush(responseString);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("IO exception occurred.", cause);
        ctx.close();
    }


}
