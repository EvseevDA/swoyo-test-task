package com.github.evseevda.swoyotesttask.core.domain.topic;

import com.github.evseevda.swoyotesttask.core.domain.exception.TopicAlreadyExistsException;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class TopicsTest {

    private Topics topics;
    private Topic topic1;
    private Topic topic2;


    @BeforeEach
    void setUp() {
        topics = new Topics();

        topic1 = new Topic();
        topic1.setName("Topic 1");

        topic2 = new Topic();
        topic2.setName("Topic 2");
    }

    @Test
    void givenTopicsList_WhenAddTopicIsCalled_ThenTopicIsAdded() {
        // arrange
        Topic newTopic = new Topic();
        newTopic.setName("New Topic");

        // action
        topics.addTopic(newTopic);

        // assertion
        Assertions.assertTrue(topics.getTopicByName("New Topic").isPresent());
    }

    @Test
    void givenExistingTopic_WhenAddTopicIsCalled_ThenThrowTopicAlreadyExistsException() {
        // arrange
        topics.addTopic(topic1);

        // action & assertion
        Assertions.assertThrows(TopicAlreadyExistsException.class, () -> topics.addTopic(topic1));
    }

    @Test
    void givenTopicName_WhenGetTopicByNameIsCalled_ThenReturnCorrectTopic() {
        // arrange
        topics.addTopic(topic1);
        String topicName = "Topic 1";

        // action
        Optional<Topic> result = topics.getTopicByName(topicName);

        // assertion
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(topicName, result.get().getName());
    }

    @Test
    void givenTopicNameAndVoteName_WhenGetVoteIsCalled_ThenReturnCorrectVote() {
        // arrange
        Vote vote = new Vote();
        vote.setName("Vote 1");
        topic1.addVote(vote);
        topics.addTopic(topic1);

        // action
        Optional<Vote> result = topics.getVote("Topic 1", "Vote 1");

        // assertion
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Vote 1", result.get().getName());
    }

    @Test
    void givenTopicNameAndVoteName_WhenDeleteVoteIsCalled_ThenVoteIsRemoved() {
        // arrange
        Vote vote = new Vote();
        vote.setName("Vote 1");
        topic1.addVote(vote);
        topics.addTopic(topic1);

        // action
        topics.deleteVote("Topic 1", "Vote 1");

        // assertion
        Assertions.assertFalse(topics.getVote("Topic 1", "Vote 1").isPresent());
    }

    @Test
    void givenTopicsList_WhenToStringIsCalled_ThenReturnCorrectString() {
        // arrange
        topics.addTopic(topic1);
        topics.addTopic(topic2);
        String expected = "Topic 1 (votes in topic=0)\nTopic 2 (votes in topic=0)\n";

        // action
        String result = topics.toString();

        // assertion
        Assertions.assertEquals(expected, result);
    }

}