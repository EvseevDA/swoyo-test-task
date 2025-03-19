package com.github.evseevda.swoyotesttask.core.domain.vote;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Votes {

    private List<Vote> votes = new ArrayList<>();

    @JsonIgnore
    private final Representator representator = new Representator();

    public int count() {
        return votes.size();
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public boolean contains(Vote vote) {
        return votes.contains(vote);
    }

    public Optional<Vote> getVoteByName(String voteName) {
        for (Vote vote : votes) {
            if (vote.getName().equals(voteName)) {
                return Optional.of(vote);
            }
        }
        return Optional.empty();
    }

    public void deleteVote(String voteName) {
        votes.removeIf(v -> v.getName().equals(voteName));
    }

    @Override
    public String toString() {
        return representator.onlyNamesRepresentation();
    }

    public class Representator {

        public String onlyNamesRepresentation() {
            StringBuilder sb = new StringBuilder();
            votes.forEach(vote -> sb.append(vote.getName()).append('\n'));
            return sb.toString();
        }

    }

}
