package com.github.evseevda.swoyotesttask.server.repository;

import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TopicRepositoryImplTest {

    private TopicRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new TopicRepositoryImpl();
    }

    @Test
    void givenNewRepository_WhenGetTopicsIsCalled_ThenReturnEmptyTopics() {
        // action
        Topics topics = repository.getTopics();

        // assertion
        assertNotNull(topics);
        assertTrue(topics.getTopics().isEmpty());
    }

    @Test
    void givenNewTopics_WhenSetTopicsIsCalled_ThenUpdateRepository() {
        // arrange
        Topics newTopics = new Topics();

        // action
        repository.setTopics(newTopics);

        // assertion
        assertEquals(newTopics, repository.getTopics());
    }

    @Test
    void givenRepository_WhenSetTopicsIsCalled_ThenReturnUpdatedTopics() {
        // arrange
        Topics newTopics = new Topics();

        // action
        Topics result = repository.setTopics(newTopics);

        // assertion
        assertEquals(newTopics, result);
    }

}