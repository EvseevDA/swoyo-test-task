package com.github.evseevda.swoyotesttask.server.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.server.security.context.SecurityContext;
import org.springframework.stereotype.Component;

import static com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutionResult.OK;

@Component
public class LoginCommandExecutor implements ClientCommandExecutor {

    @Override
    public String execute(Command command) {
        String username = command.getArgs().get("u");
        SecurityContext.setCurrentUser(new User(username));
        return OK.name();
    }

    @Override
    public String command() {
        return "login";
    }

}
