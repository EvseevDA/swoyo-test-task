package com.github.evseevda.swoyotesttask.client.command.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public abstract class ClientCommandServerDelegatingExecutor extends ExceptionHandlingClientCommandExecutor {

    private final Channel channel;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected String logic(Command command) throws Exception {
        wrapAndSend(command);
        return "executed";
    }

    private void wrapAndSend(Command command) throws JsonProcessingException, InterruptedException {
        ServerRequest serverRequest = new ServerRequest(RequestAction.EXECUTE_COMMAND, command.toString());
        String requestString = objectMapper.writeValueAsString(serverRequest);
        channel.writeAndFlush(requestString + System.lineSeparator()).await();
    }

}
