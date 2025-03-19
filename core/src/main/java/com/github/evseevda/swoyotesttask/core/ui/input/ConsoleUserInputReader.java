package com.github.evseevda.swoyotesttask.core.ui.input;

import com.github.evseevda.swoyotesttask.core.ui.output.MessageWriter;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class ConsoleUserInputReader implements UserInputReader {

    private final MessageWriter messageWriter;
    private final Scanner scanner = new Scanner(System.in);
    private final String space = " ";

    @Override
    public int readInt() {
        int scanned = scanner.nextInt();
        scanner.skip(Pattern.compile("[ \n]"));
        return scanned;
    }

    @Override
    public int readInt(String message) {
        messageWriter.write(message + space);
        return readInt();
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public String readString(String message) {
        messageWriter.write(message + space);
        return readString();
    }

}
