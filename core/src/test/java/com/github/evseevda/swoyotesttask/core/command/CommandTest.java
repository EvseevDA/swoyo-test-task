package com.github.evseevda.swoyotesttask.core.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

class CommandTest {

    @Test
    void givenCommandAsStringAndAsCommandObject_WhenToStringForCommandObjectIsCalled_ThenResultIsCommandAsString() {
        // arrange
        String expected = "test -n=to string test -r=this string -c=CommandTest";

        Map<String, String> args = new LinkedHashMap<>();
        args.put("n", "to string test");
        args.put("r", "this string");
        args.put("c", "CommandTest");
        Command objectCommand = new Command(
                "test",
                args
        );

        // action
        String actual = objectCommand.toString();

        // assertion
        Assertions.assertEquals(expected, actual);
    }

}