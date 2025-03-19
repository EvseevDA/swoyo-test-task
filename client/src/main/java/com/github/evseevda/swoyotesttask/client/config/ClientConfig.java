package com.github.evseevda.swoyotesttask.client.config;

import com.github.evseevda.swoyotesttask.client.netty.handler.ResponseHandler;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private final List<ResponseHandler> responseHandlers;

    @Bean
    public Map<RequestAction, ResponseHandler> responseHandlerMap() {
        return responseHandlers.stream()
                .collect(toMap(ResponseHandler::action, Function.identity()));
    }

}
