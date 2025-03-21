package com.github.evseevda.swoyotesttask.core.messaging.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ServerRequest {

    private RequestAction action;
    private String body;

    public static ServerRequest exit() {
        return ServerRequest.builder()
                .action(RequestAction.EXIT)
                .build();
    }

}
