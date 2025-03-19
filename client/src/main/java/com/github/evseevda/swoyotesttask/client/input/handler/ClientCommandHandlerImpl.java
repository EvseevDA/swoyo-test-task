package com.github.evseevda.swoyotesttask.client.input.handler;

import com.github.evseevda.swoyotesttask.core.command.executor.facade.ClientCommandExecutorFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientCommandHandlerImpl implements ClientCommandHandler {

    private final ClientCommandExecutorFacade clientCommandExecutorFacade;

    @Override
    public void handle(String command) {
        clientCommandExecutorFacade.execute(command);
    }

}
