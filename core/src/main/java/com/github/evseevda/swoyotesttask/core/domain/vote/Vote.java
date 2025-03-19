package com.github.evseevda.swoyotesttask.core.domain.vote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answer;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Vote {

    @EqualsAndHashCode.Include
    private String name;

    private User owner;

    private VoteInfo info;

    @JsonIgnore
    private final Analytics analytics = new Analytics();

    public void vote(User voter, int answerPosition) {
        info.getAnswers().vote(voter, answerPosition);
    }

    @Override
    public String toString() {
        return info.getRepresentator().defaultRepresentation();
    }

    public class Analytics {

        public Answer getLeaderAnswer() {
            return info.getAnswers().getAnalytics().getLeaderAnswer();
        }

    }

}
