package com.github.evseevda.swoyotesttask.core.messaging.request.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DoVoteRequestBody {

    private String topic;
    private String vote;
    private int answerPosition;

}
