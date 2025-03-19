package com.github.evseevda.swoyotesttask.client.netty.config;

import com.github.evseevda.swoyotesttask.client.netty.client.AsbtractNettyClient;
import com.github.evseevda.swoyotesttask.client.netty.client.NettyClient;
import com.github.evseevda.swoyotesttask.client.input.handler.ClientCommandHandler;
import com.github.evseevda.swoyotesttask.core.ui.input.UserInputReader;
import com.github.evseevda.swoyotesttask.core.ui.output.MessageWriter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NettyClientConfig {

    private final EventLoopGroup eventLoopGroup;
    private final Channel channel;
    private final Bootstrap bootstrap;

    private final ClientCommandHandler clientCommandHandler;
    private final UserInputReader userInputReader;
    private final ClientExecutionState clientExecutionState;
    private final MessageWriter messageWriter;

    @Bean
    public NettyClient nettyClient() {
        return new AsbtractNettyClient(eventLoopGroup, userInputReader, messageWriter, clientExecutionState) {
            @Override
            public Bootstrap configureBoostrap(EventLoopGroup group) {
                return bootstrap;
            }

            @Override
            public Channel configureChannel() throws InterruptedException {
                return channel;
            }

            @Override
            public ClientCommandHandler clientCommandHandler() {
                return clientCommandHandler;
            }
        };
    }


}
