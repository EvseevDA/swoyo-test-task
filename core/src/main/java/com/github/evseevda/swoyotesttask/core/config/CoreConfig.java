package com.github.evseevda.swoyotesttask.core.config;

import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.executor.ServerCommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.executor.facade.ClientCommandExecutorFacade;
import com.github.evseevda.swoyotesttask.core.command.executor.facade.ClientCommandExecutorFacadeImpl;
import com.github.evseevda.swoyotesttask.core.command.executor.facade.ServerCommandExecutorFacade;
import com.github.evseevda.swoyotesttask.core.command.executor.facade.ServerCommandExecutorFacadeImpl;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParser;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParserImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;


@Configuration
@ComponentScan("com.github.evseevda.swoyotesttask")
@RequiredArgsConstructor
public class CoreConfig {

    private final List<ServerCommandExecutor> serverCommandExecutors;
    private final List<ClientCommandExecutor> clientCommandExecutors;

    @Bean
    public CommandParser commandParser() {
        return new CommandParserImpl();
    }

    @Bean
    public Map<String, ServerCommandExecutor> serverCommandExecutorMap() {
        return serverCommandExecutors.stream()
                .collect(toMap(CommandExecutor::command, Function.identity()));
    }

    @Bean
    public ServerCommandExecutorFacade serverCommandExecutorFacade() {
        return new ServerCommandExecutorFacadeImpl(serverCommandExecutorMap(), commandParser());
    }

    @Bean
    public Map<String, ClientCommandExecutor> clientCommandExecutorMap() {
        return clientCommandExecutors.stream()
                .collect(toMap(CommandExecutor::command, Function.identity()));
    }

    @Bean
    public ClientCommandExecutorFacade clientCommandExecutorFacade() {
        return new ClientCommandExecutorFacadeImpl(clientCommandExecutorMap(), commandParser());
    }

}
