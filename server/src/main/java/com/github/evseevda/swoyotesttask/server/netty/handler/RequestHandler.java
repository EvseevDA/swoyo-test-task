package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import io.netty.channel.ChannelHandlerContext;

public interface RequestHandler {

    void handle(ServerRequest request, ChannelHandlerContext context);
    RequestAction requestAction();

}
