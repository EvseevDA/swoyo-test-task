package com.github.evseevda.swoyotesttask.core.domain.vote;

import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answer;
import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class VoteTest {

    private Vote vote;
    private User owner;
    private User voter;


    @BeforeEach
    void setUp() {
        owner = new User();
        owner.setUsername("owner");

        voter = new User();
        voter.setUsername("voter");

        Answers answers = new Answers(Arrays.asList(
                new Answer("Answer 1"),
                new Answer("Answer 2")
        ));

        VoteInfo voteInfo = new VoteInfo("Test Vote Description", answers);
        vote = new Vote();
        vote.setName("Test Vote");
        vote.setOwner(owner);
        vote.setInfo(voteInfo);
    }

    @Test
    void givenVoteWithInfo_WhenToStringIsCalled_ThenReturnCorrectString() {
        // arrange
        String expected = "Test Vote Description\n1. Answer 1\t0\n2. Answer 2\t0\n";

        // action
        String result = vote.toString();

        // assertion
        Assertions.assertEquals(expected, result);
    }

    @Test
    void givenVoterAndAnswerPosition_WhenVoteIsCalled_ThenVoterIsAddedToAnswer() {
        // arrange
        int answerPosition = 1;

        // action
        vote.vote(voter, answerPosition);

        // assertion
        Assertions.assertEquals(1, vote.getInfo().getAnswers().getByPosition(answerPosition).votersCount());
        Assertions.assertTrue(vote.getInfo().getAnswers().getByPosition(answerPosition).getVotedUsers().contains(voter));
    }

    @Test
    void givenVoteWithVotes_WhenGetLeaderAnswerIsCalled_ThenReturnAnswerWithMostVotes() {
        // arrange
        vote.vote(voter, 1);

        // action
        Answer leaderAnswer = vote.getAnalytics().getLeaderAnswer();

        // assertion
        Assertions.assertNotNull(leaderAnswer);
        Assertions.assertEquals("Answer 1", leaderAnswer.getAnswer());
    }

}