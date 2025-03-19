package com.github.evseevda.swoyotesttask.core.domain.vote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answers;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class VoteInfo {

    private String description;

    private Answers answers;

    @JsonIgnore
    private final Representator representator = new Representator();

    public VoteInfo(String description, Answers answers) {
        this.description = description;
        this.answers = answers;
    }

    public class Representator {

        public String defaultRepresentation() {
            return description + '\n' +
                    answers.getRepresentator().defaultRepresentation();
        }

    }

}
