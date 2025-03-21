package com.github.evseevda.swoyotesttask.client.netty.client;

import com.github.evseevda.swoyotesttask.client.input.handler.ClientCommandHandler;
import com.github.evseevda.swoyotesttask.client.netty.config.ClientExecutionState;
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

    @Override
    public void start() throws InterruptedException {
        try {
            configureBoostrap(eventLoopGroup);
            Channel channel = configureChannel();
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
            handleCommand(command);
            waitForResponse();
        }
    }

    public boolean stopped() {
        return clientExecutionState.isStopped();
    }

    private void handleCommand(String command) {
        try {
            clientCommandHandler().handle(command);
        } catch (UnknownCommandException e) {
            messageWriter.writeln("Неизвестная команда");
        }
    }

    public abstract ClientCommandHandler clientCommandHandler();

    private void waitForResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
