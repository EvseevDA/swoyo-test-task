package com.github.evseevda.swoyotesttask.server.config;

import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.server.netty.handler.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Configuration
@RequiredArgsConstructor
public class ServerConfig {

    private final List<RequestHandler> requestHandlers;

    @Bean
    public Map<RequestAction, RequestHandler> requestHandlerMap() {
        return requestHandlers.stream()
                .collect(toMap(RequestHandler::requestAction, Function.identity()));
    }

}
