package com.github.evseevda.swoyotesttask.server.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.domain.exception.TopicAlreadyExistsException;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topic;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static com.github.evseevda.swoyotesttask.core.messaging.response.ResponseStatus.OK;
import static org.apache.logging.log4j.Level.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CreateTopicCommandExecutorTest {

    private TopicRepository topicRepository;
    private CreateTopicCommandExecutor executor;

    @BeforeEach
    void setUp() {
        topicRepository = Mockito.mock(TopicRepository.class);
        executor = new CreateTopicCommandExecutor(topicRepository);
    }

    @Test
    void givenValidCommand_WhenExecuteIsCalled_ThenReturnOk() {
        // arrange
        Command command = new Command("create topic", Map.of("n", "New Topic"));
        Topics topics = Mockito.mock(Topics.class);
        when(topicRepository.getTopics()).thenReturn(topics);

        // action
        String result = executor.execute(command);

        // assertion
        assertEquals(OK.name(), result);
        verify(topics, times(1)).addTopic(any(Topic.class));
    }

    @Test
    void givenExistingTopic_WhenExecuteIsCalled_ThenReturnError() {
        // arrange
        Command command = new Command("create topic", Map.of("n", "Existing Topic"));
        Topics topics = Mockito.mock(Topics.class);
        when(topicRepository.getTopics()).thenReturn(topics);
        doThrow(TopicAlreadyExistsException.class).when(topics).addTopic(any(Topic.class));

        // action
        String result = executor.execute(command);

        // assertion
        assertEquals(ERROR.name(), result);
    }

}