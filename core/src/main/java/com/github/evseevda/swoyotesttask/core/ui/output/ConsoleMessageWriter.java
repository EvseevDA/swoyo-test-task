package com.github.evseevda.swoyotesttask.core.ui.output;

public class ConsoleMessageWriter implements MessageWriter {
    @Override
    public void write(String message) {
        System.out.print(message);
    }

    @Override
    public void writeln(String message) {
        System.out.println(message);
    }

}
