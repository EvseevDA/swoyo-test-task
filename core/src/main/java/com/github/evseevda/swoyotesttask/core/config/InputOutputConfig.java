package com.github.evseevda.swoyotesttask.core.config;

import com.github.evseevda.swoyotesttask.core.ui.input.ConsoleUserInputReader;
import com.github.evseevda.swoyotesttask.core.ui.input.UserInputReader;
import com.github.evseevda.swoyotesttask.core.ui.output.ConsoleMessageWriter;
import com.github.evseevda.swoyotesttask.core.ui.output.MessageWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InputOutputConfig {

    @Bean
    public MessageWriter messageWriter() {
        return new ConsoleMessageWriter();
    }

    @Bean
    public UserInputReader userInputReader() {
        return new ConsoleUserInputReader(messageWriter());
    }

}
