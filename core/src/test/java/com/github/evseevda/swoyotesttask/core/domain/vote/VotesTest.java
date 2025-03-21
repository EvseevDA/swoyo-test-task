package com.github.evseevda.swoyotesttask.core.domain.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class VotesTest {

    private Votes votes;
    private Vote vote1;
    private Vote vote2;


    @BeforeEach
    void setUp() {
        votes = new Votes();

        vote1 = new Vote();
        vote1.setName("Vote 1");

        vote2 = new Vote();
        vote2.setName("Vote 2");

        votes.addVote(vote1);
        votes.addVote(vote2);
    }

    @Test
    void givenVotesList_WhenCountIsCalled_ThenReturnCorrectCount() {
        // arrange
        int expectedCount = 2;

        // action
        int actualCount = votes.count();

        // assertion
        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    void givenVote_WhenAddVoteIsCalled_ThenVoteIsAdded() {
        // arrange
        Vote newVote = new Vote();
        newVote.setName("New Vote");

        // action
        votes.addVote(newVote);

        // assertion
        Assertions.assertTrue(votes.contains(newVote));
    }

    @Test
    void givenVoteName_WhenGetVoteByNameIsCalled_ThenReturnCorrectVote() {
        // arrange
        String voteName = "Vote 1";

        // action
        Optional<Vote> result = votes.getVoteByName(voteName);

        // assertion
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(voteName, result.get().getName());
    }

    @Test
    void givenVoteName_WhenDeleteVoteIsCalled_ThenVoteIsRemoved() {
        // arrange
        String voteName = "Vote 1";

        // action
        votes.deleteVote(voteName);

        // assertion
        Assertions.assertFalse(votes.getVoteByName(voteName).isPresent());
    }

    @Test
    void givenVotesList_WhenToStringIsCalled_ThenReturnCorrectString() {
        // arrange
        String expected = "Vote 1\nVote 2\n";

        // action
        String result = votes.toString();

        // assertion
        Assertions.assertEquals(expected, result);
    }

}