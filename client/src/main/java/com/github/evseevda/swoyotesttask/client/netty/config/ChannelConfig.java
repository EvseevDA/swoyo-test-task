package com.github.evseevda.swoyotesttask.client.netty.config;

import com.github.evseevda.swoyotesttask.client.netty.ClientHandlerEntryPoint;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
public class ChannelConfig {

    private final ClientHandlerEntryPoint clientHandlerEntryPoint;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private int port;

    @Bean
    public EventLoopGroup eventLoopGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    @Lazy
    public Bootstrap bootstrap(EventLoopGroup eventLoopGroup) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder(), new StringEncoder(), clientHandlerEntryPoint);
                    }
                });

        return bootstrap;
    }

    @Bean
    @Lazy
    public Channel channel() throws InterruptedException {
        ChannelFuture future = bootstrap(eventLoopGroup()).connect(host, port).sync();
        return future.channel();
    }

}
