package com.github.evseevda.swoyotesttask.server.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.domain.exception.TopicAlreadyExistsException;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topic;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import com.github.evseevda.swoyotesttask.server.infrastructure.annotation.AuthenticationRequired;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutionResult.ERROR;
import static com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutionResult.OK;

@Component
@RequiredArgsConstructor
public class CreateTopicCommandExecutor implements ClientCommandExecutor {

    private final TopicRepository topicRepository;

    @Override
    @AuthenticationRequired
    public String execute(Command command) {
        Topic topic = createTopic(command);
        return tryAddTopic(topic);
    }

    private Topic createTopic(Command command) {
        String topicName = command.getArgs().get("n");
        Topic topic = Topic.builder()
                .name(topicName)
                .build();
        return topic;
    }

    private String tryAddTopic(Topic topic) {
        try {
            Topics topics = topicRepository.getTopics();
            topics.addTopic(topic);
            return OK.name();
        } catch (TopicAlreadyExistsException e) {
            return ERROR.name();
        }
    }

    @Override
    public String command() {
        return "create topic";
    }
}
