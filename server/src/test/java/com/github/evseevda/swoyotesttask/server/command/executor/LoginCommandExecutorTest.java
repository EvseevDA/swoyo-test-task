package com.github.evseevda.swoyotesttask.server.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.server.security.context.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.github.evseevda.swoyotesttask.core.messaging.response.ResponseStatus.OK;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginCommandExecutorTest {

    private LoginCommandExecutor executor;

    @BeforeEach
    void setUp() {
        executor = new LoginCommandExecutor();
    }

    @Test
    void givenValidCommand_WhenExecuteIsCalled_ThenSetCurrentUser() {
        // arrange
        Command command = new Command("login", Map.of("u", "user"));

        // action
        String result = executor.execute(command);

        // assertion
        assertEquals(OK.name(), result);
        assertEquals("user", SecurityContext.getCurrentUser().getUsername());
    }

}