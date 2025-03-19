package com.github.evseevda.swoyotesttask.server.input.handler;

import com.github.evseevda.swoyotesttask.core.command.executor.facade.ServerCommandExecutorFacade;
import com.github.evseevda.swoyotesttask.server.netty.server.config.ServerExecutionState;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServerCommandHandlerImpl implements ServerCommandHandler {

    private final ServerCommandExecutorFacade serverCommandExecutorFacade;
    private final ServerExecutionState serverExecutionState;

    @Override
    public void handle(String command, Channel serverChannel) {
        if (command.equals("exit")) {
            serverExecutionState.setStopped(true);
            serverChannel.close().awaitUninterruptibly();
        } else {
            serverCommandExecutorFacade.execute(command);
        }
    }

}
