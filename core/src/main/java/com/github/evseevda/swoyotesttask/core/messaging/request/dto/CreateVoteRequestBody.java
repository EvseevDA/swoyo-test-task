package com.github.evseevda.swoyotesttask.core.messaging.request.dto;

import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateVoteRequestBody {

    private Vote vote;
    private String topicName;

}
