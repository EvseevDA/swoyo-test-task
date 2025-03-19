package com.github.evseevda.swoyotesttask.server.input.handler;

import io.netty.channel.Channel;

public interface ServerCommandHandler {

    void handle(String command, Channel serverChannel);

}
