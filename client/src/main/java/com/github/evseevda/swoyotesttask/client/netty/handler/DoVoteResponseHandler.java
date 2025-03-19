package com.github.evseevda.swoyotesttask.client.netty.handler;

import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.response.ServerResponse;
import com.github.evseevda.swoyotesttask.core.ui.output.MessageWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoVoteResponseHandler implements ResponseHandler {

    private final MessageWriter messageWriter;

    @Override
    public RequestAction action() {
        return RequestAction.DO_VOTE;
    }

    @Override
    public void handle(ServerResponse response) {
        messageWriter.writeln("Status: " + response.getStatus());
    }
}
