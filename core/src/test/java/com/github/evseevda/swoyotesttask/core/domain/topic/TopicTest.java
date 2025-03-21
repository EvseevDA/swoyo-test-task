package com.github.evseevda.swoyotesttask.core.domain.topic;

import com.github.evseevda.swoyotesttask.core.domain.exception.VoteAlreadyExistsException;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class TopicTest {

    private Topic topic;
    private Vote vote1;
    private Vote vote2;


    @BeforeEach
    void setUp() {
        topic = new Topic();
        topic.setName("Test Topic");

        vote1 = new Vote();
        vote1.setName("Vote 1");

        vote2 = new Vote();
        vote2.setName("Vote 2");
    }

    @Test
    void givenTopicWithNoVotes_WhenVotesCountIsCalled_ThenReturnZero() {
        // arrange
        int expectedCount = 0;

        // action
        int actualCount = topic.votesCount();

        // assertion
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    void givenTopicWithOneVote_WhenVotesCountIsCalled_ThenReturnOne() {
        // arrange
        topic.addVote(vote1);

        // action
        int actualCount = topic.votesCount();

        // assertion
        Assertions.assertEquals(1, actualCount);
    }

    @Test
    void givenTopicWithMultipleVotes_WhenVotesCountIsCalled_ThenReturnCorrectCount() {
        // arrange
        topic.addVote(vote1);
        topic.addVote(vote2);

        // action
        int actualCount = topic.votesCount();

        // assertion
        Assertions.assertEquals(2, actualCount);
    }

    @Test
    void givenVote_WhenAddVoteIsCalled_ThenVoteIsAdded() {
        // arrange
        Vote newVote = new Vote();
        newVote.setName("New Vote");

        // action
        topic.addVote(newVote);

        // assertion
        Assertions.assertTrue(topic.getVotes().contains(newVote));
    }

    @Test
    void givenExistingVote_WhenAddVoteIsCalled_ThenThrowVoteAlreadyExistsException() {
        // arrange
        topic.addVote(vote1);

        // action & assertion
        Assertions.assertThrows(VoteAlreadyExistsException.class, () -> topic.addVote(vote1));
    }

    @Test
    void givenVoteName_WhenGetVoteByNameIsCalled_ThenReturnCorrectVote() {
        // arrange
        topic.addVote(vote1);
        String voteName = "Vote 1";

        // action
        Optional<Vote> result = topic.getVoteByName(voteName);

        // assertion
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(voteName, result.get().getName());
    }

    @Test
    void givenTopic_WhenDefaultRepresentationIsCalled_ThenReturnCorrectString() {
        // arrange
        topic.addVote(vote1);
        String expected = "Test Topic (votes in topic=1)";

        // action
        String result = topic.getRepresentator().defaultRepresentation();

        // assertion
        Assertions.assertEquals(expected, result);
    }

    @Test
    void givenTopic_WhenDetailedRepresentationIsCalled_ThenReturnCorrectString() {
        // arrange
        topic.addVote(vote1);
        String expected = "Test Topic\n\nVote 1\n";

        // action
        String result = topic.getRepresentator().detailedRepresentation();

        // assertion
        Assertions.assertEquals(expected, result);
    }

}