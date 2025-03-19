package com.github.evseevda.swoyotesttask.core.domain.vote.answer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.evseevda.swoyotesttask.core.domain.exception.UserAlreadyVotedException;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import lombok.*;

import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(value = "analytics", allowGetters = true)
public class Answers {

    private List<Answer> answers;

    @JsonIgnore
    private final Representator representator = new Representator();

    private final Analytics analytics = new Analytics();

    public Answers(List<Answer> answers) {
        this.answers = answers;
    }

    public Answer getByPosition(int position) {
        return answers.get(position - 1);
    }

    public void vote(User user, int answerPosition) {
        requireUserNotVoted(user);
        getByPosition(answerPosition)
                .addVoter(user);
    }

    private void requireUserNotVoted(User user) {
        for (Answer answer : answers) {
            if (answer.getVotedUsers().contains(user)) {
                throw new UserAlreadyVotedException("User cannot vote twice.");
            }
        }
    }

    public class Representator {
        public String defaultRepresentation() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < answers.size(); i++) {
                Answer answer = answers.get(i);
                sb
                        .append(i + 1)
                        .append(". ")
                        .append(answer.getAnswer())
                        .append('\t')
                        .append(answer.votersCount())
                        .append('\n');
            }
            return sb.toString();
        }
    }

    @JsonIgnoreProperties(value = "leaderAnswer", allowGetters = true)
    public class Analytics {

        @JsonProperty
        public Answer getLeaderAnswer() {
            return answers.stream()
                    .max(Comparator.comparingInt(Answer::votersCount))
                    .orElse(null);
        }

    }

}
