package com.github.evseevda.swoyotesttask.client.command.executor;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewCommandExecutor extends ClientCommandServerDelegatingExecutor {

    public ViewCommandExecutor(@Autowired Channel channel) {
        super(channel);
    }

    @Override
    public String command() {
        return "view";
    }
}
