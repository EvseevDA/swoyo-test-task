package com.github.evseevda.swoyotesttask.server.netty.server;

import com.github.evseevda.swoyotesttask.core.ui.input.UserInputReader;
import com.github.evseevda.swoyotesttask.server.exception.ServerException;
import com.github.evseevda.swoyotesttask.server.input.handler.ServerCommandHandler;
import com.github.evseevda.swoyotesttask.server.netty.server.config.ServerExecutionState;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class NettyServerImpl implements NettyServer {

    private final int port;
    private Channel serverChannel;

    private final UserInputReader userInputReader;
    private final ServerCommandHandler serverCommandHandler;
    private final ServerExecutionState serverExecutionState;

    @Override
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder(), new StringEncoder(), serverHandlerEntryPoint());
                        }
                    });

            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("Server started on port " + port);
            serverChannel = future.channel();
            new Thread(this::listenConsoleCommands).start();
            serverChannel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new ServerException("Interrupted.", e);
        } finally {
            bossGroup.shutdownGracefully().awaitUninterruptibly();
            workerGroup.shutdownGracefully().awaitUninterruptibly();
        }
    }

    private void listenConsoleCommands() {
        while (!serverExecutionState.isStopped()) {
            String command = userInputReader.readString("Введите команду:");
            serverCommandHandler.handle(command, serverChannel);
        }
    }

    public abstract ChannelHandler serverHandlerEntryPoint();

}
