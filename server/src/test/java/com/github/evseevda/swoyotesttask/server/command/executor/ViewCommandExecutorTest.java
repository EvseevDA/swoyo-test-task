package com.github.evseevda.swoyotesttask.server.command.executor;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;

import static org.apache.logging.log4j.Level.ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ViewCommandExecutorTest {

    private TopicRepository topicRepository;
    private ViewCommandExecutor executor;

    @BeforeEach
    void setUp() {
        topicRepository = Mockito.mock(TopicRepository.class);
        executor = new ViewCommandExecutor(topicRepository);
    }

    @Test
    void givenValidTopicAndVote_WhenExecuteIsCalled_ThenReturnVoteInfo() {
        // arrange
        Command command = new Command("view", Map.of("t", "Topic", "v", "Vote"));
        Topics topics = Mockito.mock(Topics.class);
        when(topicRepository.getTopics()).thenReturn(topics);
        Vote vote = Mockito.mock(Vote.class);
        when(topics.getVote("Topic", "Vote")).thenReturn(Optional.of(vote));
        when(vote.toString()).thenReturn("Vote Info");

        // action
        String result = executor.execute(command);

        // assertion
        assertEquals("Vote Info", result);
    }

    @Test
    void givenInvalidTopic_WhenExecuteIsCalled_ThenReturnError() {
        // arrange
        Command command = new Command("view", Map.of("t", "Invalid Topic"));
        Topics topics = Mockito.mock(Topics.class);
        when(topicRepository.getTopics()).thenReturn(topics);
        when(topics.getTopicByName("Invalid Topic")).thenReturn(Optional.empty());

        // action
        String result = executor.execute(command);

        // assertion
        assertEquals(ERROR.name(), result);
    }

}