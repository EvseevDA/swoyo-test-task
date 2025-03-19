package com.github.evseevda.swoyotesttask.core.messaging.response;

import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ServerResponse {

    private RequestAction action;
    private ResponseStatus status;
    private String body;

    public static ServerResponse ok(RequestAction action, String body) {
        return ServerResponse.builder()
                .action(action)
                .status(ResponseStatus.OK)
                .body(body)
                .build();
    }

    public static ServerResponse error(RequestAction action, String body) {
        return ServerResponse.builder()
                .action(action)
                .status(ResponseStatus.ERROR)
                .body(body)
                .build();
    }

}
