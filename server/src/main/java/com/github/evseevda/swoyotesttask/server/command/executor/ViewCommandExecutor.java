package com.github.evseevda.swoyotesttask.server.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutionResult;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topic;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.server.infrastructure.annotation.AuthenticationRequired;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ViewCommandExecutor implements ClientCommandExecutor {

    private final TopicRepository topicRepository;

    @Override
    @AuthenticationRequired
    public String execute(Command command) {
        try {
            String topicName = command.getArgs().get("t");
            String voteName = command.getArgs().get("v");
            if (topicName != null && voteName != null) {
                return voteOrNotFoundMessage(topicName, voteName);
            } else if (topicName != null) {
                return topicOrNotFoundMessage(topicName);
            } else {
                return topicRepository.getTopics().toString();
            }
        } catch (Throwable e) {
            return CommandExecutionResult.ERROR.name();
        }
    }


    private String topicOrNotFoundMessage(String topicName) {
        Optional<Topic> topic = topicRepository.getTopics().getTopicByName(topicName);
        if (topic.isPresent()) {
            return topic.get().getVotes().toString();
        } else {
            return CommandExecutionResult.ERROR.name();
        }
    }

    private String voteOrNotFoundMessage(String topicName, String voteName) {
        Optional<Vote> vote = topicRepository.getTopics().getVote(topicName, voteName);
        if (vote.isPresent()) {
            return vote.get().toString();
        } else {
            return CommandExecutionResult.ERROR.name();
        }
    }

    @Override
    public String command() {
        return "view";
    }
}
