package com.github.evseevda.swoyotesttask.core.command.parser;

import com.github.evseevda.swoyotesttask.core.command.Command;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CommandParserImpl implements CommandParser {

    @Override
    public Command parse(String fullCommand) {
        int firstArgIndex = fullCommand.indexOf('-');
        if (argsExists(firstArgIndex)) {
            return parseCommandWithArgs(fullCommand, firstArgIndex);
        } else {
            return new Command(fullCommand.trim(), Map.of());
        }
    }

    private Command parseCommandWithArgs(String fullCommand, int firstArgIndex) {
        String command = parseCommandToString(fullCommand, firstArgIndex);
        String[] argsArray = parseArgsToArray(fullCommand, firstArgIndex);
        Map<String, String> argsMap = convertToArgsMap(argsArray);
        return new Command(command, argsMap);
    }

    private String parseCommandToString(String fullCommand, int firstArgIndex) {
        return fullCommand.substring(0, firstArgIndex - 1);
    }

    private String[] parseArgsToArray(String fullCommand, int firstArgIndex) {
        return Arrays.stream(fullCommand.substring(firstArgIndex).split("-"))
                .filter(s -> !s.isEmpty())
                .map(String::trim)
                .map(s -> s.split("="))
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }

    private Map<String, String> convertToArgsMap(String[] argsArray) {
        Map<String, String> argsMap = new LinkedHashMap<>();
        for (int i = 0; i < argsArray.length - 1; i += 2) {
            argsMap.put(argsArray[i], argsArray[i + 1]);
        }
        return argsMap;
    }

    private boolean argsExists(int firstArg) {
        return firstArg != -1;
    }

}
