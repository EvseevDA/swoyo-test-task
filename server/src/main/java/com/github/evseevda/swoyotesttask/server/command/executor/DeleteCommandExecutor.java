package com.github.evseevda.swoyotesttask.server.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutionResult;
import com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutor;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.server.infrastructure.annotation.AuthenticationRequired;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import com.github.evseevda.swoyotesttask.server.security.context.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeleteCommandExecutor implements CommandExecutor {

    private final TopicRepository topicRepository;

    @Override
    @AuthenticationRequired
    public String execute(Command command) {
        User currentUser = SecurityContext.getCurrentUser();
        String topicName = command.getArgs().get("t");
        String voteName = command.getArgs().get("v");
        Topics topics = topicRepository.getTopics();

        Optional<Vote> vote = topics.getVote(topicName, voteName);
        if (vote.isPresent()) {
            if (vote.get().getOwner().equals(currentUser)) {
                topics.deleteVote(topicName, voteName);
            } else {
                return CommandExecutionResult.ERROR.name();
            }
        }
        return CommandExecutionResult.OK.name();
    }

    @Override
    public String command() {
        return "delete";
    }
}
