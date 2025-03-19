package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

@Component
public class ExitRequestHandler implements RequestHandler {

    @Override
    public void handle(ServerRequest request, ChannelHandlerContext context) {
        context.close();
    }

    @Override
    public RequestAction requestAction() {
        return RequestAction.EXIT;
    }
}
