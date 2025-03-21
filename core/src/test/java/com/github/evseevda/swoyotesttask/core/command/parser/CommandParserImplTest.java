package com.github.evseevda.swoyotesttask.core.command.parser;

import com.github.evseevda.swoyotesttask.core.command.Command;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class CommandParserImplTest {

    private static final CommandParser PARSER = new CommandParserImpl();

    @Test
    void givenCommandAsStringWithoutArgs_WhenParserParseIsCalled_ThenReturnedCommandIsCorrect() {
        // arrange
        Command expected = new Command("test", Map.of());
        String command = "test";

        // action
        Command actual = PARSER.parse(command);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenCommandAsStringWithOneArgsWithoutSpaces_WhenParserParseIsCalled_ThenReturnedCommandIsCorrect() {
        // arrange
        Command expected = new Command("test", Map.of("a", "arg1"));
        String command = "test -a=arg1";

        // action
        Command actual = PARSER.parse(command);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenCommandAsStringWithOneArgsWithSpace_WhenParserParseIsCalled_ThenReturnedCommandIsCorrect() {
        // arrange
        Command expected = new Command("test", Map.of("a", "one two"));
        String command = "test -a=one two";

        // action
        Command actual = PARSER.parse(command);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenCommandAsStringWithTwoArgsWithoutSpaces_WhenParserParseIsCalled_ThenReturnedCommandIsCorrect() {
        // arrange
        Command expected = new Command("test", Map.of("a", "one", "b", "two"));
        String command = "test -a=one -b=two";

        // action
        Command actual = PARSER.parse(command);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void givenCommandAsStringWithTwoArgsWithSpaces_WhenParserParseIsCalled_ThenReturnedCommandIsCorrect() {
        // arrange
        Command expected = new Command("test", Map.of("a", "one two", "b", "three four"));
        String command = "test -a=one two -b=three four";

        // action
        Command actual = PARSER.parse(command);

        // assertion
        Assertions.assertEquals(expected, actual);
    }

}