package com.github.evseevda.swoyotesttask.core.domain.vote.answer;

import com.github.evseevda.swoyotesttask.core.domain.user.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Answer {

    @EqualsAndHashCode.Include
    private String answer;

    private final List<User> votedUsers = new ArrayList<>();

    public Answer(String answer) {
        this.answer = answer;
    }

    public void addVoter(User user) {
        votedUsers.add(user);
    }

    public int votersCount() {
        return votedUsers.size();
    }

    @Override
    public String toString() {
        return answer;
    }
}
