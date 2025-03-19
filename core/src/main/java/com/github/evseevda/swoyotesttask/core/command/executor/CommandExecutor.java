package com.github.evseevda.swoyotesttask.core.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;

public interface CommandExecutor {

    String execute(Command command);
    String command();

}
