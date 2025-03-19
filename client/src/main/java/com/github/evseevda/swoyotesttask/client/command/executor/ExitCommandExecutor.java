package com.github.evseevda.swoyotesttask.client.command.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.client.exception.ClientException;
import com.github.evseevda.swoyotesttask.client.netty.config.ClientExecutionState;
import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutionResult;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExitCommandExecutor implements ClientCommandExecutor {

    private final ClientExecutionState clientExecutionState;
    private final Channel channel;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String execute(Command command) {
        clientExecutionState.setStopped(true);
        try {
            makeAndSendExitRequest();
        } catch (Exception e) {
            throw new ClientException("Client exception occurred.", e);
        }
        return CommandExecutionResult.OK.name();
    }

    private void makeAndSendExitRequest() throws Exception {
        ServerRequest serverRequest = ServerRequest.exit();
        String requestString = objectMapper.writeValueAsString(serverRequest);
        channel.writeAndFlush(requestString).sync();
    }

    @Override
    public String command() {
        return "exit";
    }
}
