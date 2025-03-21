package com.github.evseevda.swoyotesttask.server.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import com.github.evseevda.swoyotesttask.server.security.context.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;

import static com.github.evseevda.swoyotesttask.core.messaging.response.ResponseStatus.OK;
import static org.apache.logging.log4j.Level.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeleteCommandExecutorTest {

    private TopicRepository topicRepository;
    private DeleteCommandExecutor executor;

    @BeforeEach
    void setUp() {
        topicRepository = Mockito.mock(TopicRepository.class);
        executor = new DeleteCommandExecutor(topicRepository);
    }

    @Test
    void givenValidCommand_WhenExecuteIsCalled_ThenReturnOk() {
        // arrange
        User testUser = User.builder().username("test").build();
        SecurityContext.setCurrentUser(testUser);
        Command command = new Command("delete", Map.of("t", "Topic", "v", "Vote"));
        Topics topics = Mockito.mock(Topics.class);
        when(topicRepository.getTopics()).thenReturn(topics);
        Vote vote = Mockito.mock(Vote.class);
        when(topics.getVote("Topic", "Vote")).thenReturn(Optional.of(vote));
        when(vote.getOwner()).thenReturn(testUser);

        // action
        String result = executor.execute(command);

        // assertion
        assertEquals(OK.name(), result);
        verify(topics, times(1)).deleteVote("Topic", "Vote");
    }

    @Test
    void givenInvalidOwner_WhenExecuteIsCalled_ThenReturnError() {
        // arrange
        Command command = new Command("delete", Map.of("t", "Topic", "v", "Vote"));
        Topics topics = Mockito.mock(Topics.class);
        when(topicRepository.getTopics()).thenReturn(topics);
        Vote vote = Mockito.mock(Vote.class);
        when(topics.getVote("Topic", "Vote")).thenReturn(Optional.of(vote));
        when(vote.getOwner()).thenReturn(new User("Another User"));

        // action
        String result = executor.execute(command);

        // assertion
        assertEquals(ERROR.name(), result);
    }

}