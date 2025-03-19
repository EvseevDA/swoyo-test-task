package com.github.evseevda.swoyotesttask.server.netty.server.config;

import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.ui.input.UserInputReader;
import com.github.evseevda.swoyotesttask.server.input.handler.ServerCommandHandler;
import com.github.evseevda.swoyotesttask.server.netty.ServerHandlerEntryPoint;
import com.github.evseevda.swoyotesttask.server.netty.handler.RequestHandler;
import com.github.evseevda.swoyotesttask.server.netty.server.NettyServerImpl;
import io.netty.channel.ChannelHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class NettyServerConfig {

    private final Map<RequestAction, RequestHandler> requestHandlerMap;
    private final UserInputReader userInputReader;
    private final ServerCommandHandler serverCommandHandler;
    private final ServerExecutionState serverExecutionState;

    @Value("${server.port}")
    private int port;

    @Bean
    @Scope("prototype")
    public ChannelHandler serverEntryPointHandler() {
        return new ServerHandlerEntryPoint(requestHandlerMap);
    }


    @Bean
    public NettyServerImpl nettyServer() {
        return new NettyServerImpl(port, userInputReader, serverCommandHandler, serverExecutionState) {
            @Override
            public ChannelHandler serverHandlerEntryPoint() {
                return serverEntryPointHandler();
            }
        };
    }

}
