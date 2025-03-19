package com.github.evseevda.swoyotesttask.client.netty.client;

import com.github.evseevda.swoyotesttask.client.netty.config.ClientExecutionState;
import com.github.evseevda.swoyotesttask.client.input.handler.ClientCommandHandler;
import com.github.evseevda.swoyotesttask.core.domain.exception.UnknownCommandException;
import com.github.evseevda.swoyotesttask.core.ui.input.UserInputReader;
import com.github.evseevda.swoyotesttask.core.ui.output.MessageWriter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AsbtractNettyClient implements NettyClient {

    private final EventLoopGroup eventLoopGroup;
    private final UserInputReader userInputReader;
    private final MessageWriter messageWriter;
    private final ClientExecutionState clientExecutionState;
    private Channel channel;
    private Bootstrap bootstrap;

    @Override
    public void start() throws InterruptedException {
        try {
            bootstrap = configureBoostrap(eventLoopGroup);
            channel = configureChannel();
            new Thread(this::clientLogic).start();
            channel.closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().await();
        }
    }

    public abstract Bootstrap configureBoostrap(EventLoopGroup group);

    public abstract Channel configureChannel() throws InterruptedException;

    public void clientLogic() {
        while (!stopped()) {
            String command = userInputReader.readString("Введите команду:");
            try {
                clientCommandHandler().handle(command);
            } catch (UnknownCommandException e) {
                messageWriter.writeln("Неизвестная команда");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public abstract ClientCommandHandler clientCommandHandler();

    public boolean stopped() {
        return clientExecutionState.isStopped();
    }

}
