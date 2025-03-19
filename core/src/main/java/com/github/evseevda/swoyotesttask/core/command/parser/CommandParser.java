package com.github.evseevda.swoyotesttask.core.command.parser;

import com.github.evseevda.swoyotesttask.core.command.Command;

public interface CommandParser {

    Command parse(String fullCommand);

}
