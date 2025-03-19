package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.command.executor.facade.ClientCommandExecutorFacade;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.response.ResponseStatus;
import com.github.evseevda.swoyotesttask.core.messaging.response.ServerResponse;
import com.github.evseevda.swoyotesttask.server.exception.ServerException;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecuteCommandRequestHandler implements RequestHandler {

    private final ClientCommandExecutorFacade clientCommandExecutorFacade;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public RequestAction requestAction() {
        return RequestAction.EXECUTE_COMMAND;
    }

    @Override
    public void handle(ServerRequest request, ChannelHandlerContext context) {
        try {
            String command = request.getBody();
            String commandOutput = clientCommandExecutorFacade.execute(command);
            writeSuccessResponse(context, commandOutput);
        } catch (Throwable e) {
            handleError(context);
        }
    }

    private void writeSuccessResponse(ChannelHandlerContext context, String commandOutput) throws JsonProcessingException {
        ServerResponse response = ServerResponse.ok(RequestAction.EXECUTE_COMMAND, commandOutput);
        String responseString = objectMapper.writeValueAsString(response);
        context.writeAndFlush(responseString);
    }

    private void handleError(ChannelHandlerContext context) {
        ServerResponse response = ServerResponse.error(RequestAction.EXECUTE_COMMAND, "Some error occurred.");
        try {
            String stringResponse = objectMapper.writeValueAsString(response);
            context.writeAndFlush(stringResponse);
        } catch (JsonProcessingException ex) {
            throw new ServerException("Server exception occurred.", ex);
        }
    }

}
