package com.github.evseevda.swoyotesttask.core.domain.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.evseevda.swoyotesttask.core.domain.exception.VoteAlreadyExistsException;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.core.domain.vote.Votes;
import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Topic {

    private String name;

    @Builder.Default
    private Votes votes = new Votes();

    @JsonIgnore
    private final Representator representator = new Representator();

    public int votesCount() {
        return votes.count();
    }

    public void addVote(Vote vote) {
        requireVoteNotExists(vote);
        votes.addVote(vote);
    }

    public Optional<Vote> getVoteByName(String voteName) {
        return votes.getVoteByName(voteName);
    }

    private void requireVoteNotExists(Vote vote) {
        if (votes.contains(vote)) {
            throw new VoteAlreadyExistsException(
                    "Vote '%s' already exists in topic '%s'."
                            .formatted(vote.getName(), name)
            );
        }
    }

    public class Representator {

        public String defaultRepresentation() {
            return "%s (votes in topic=%s)"
                    .formatted(name, votesCount());
        }

        public String detailedRepresentation() {
            return name + "\n\n"
                    + votes.getRepresentator().onlyNamesRepresentation();
        }

    }

}
