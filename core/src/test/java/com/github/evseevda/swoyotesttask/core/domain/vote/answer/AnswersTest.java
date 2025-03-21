package com.github.evseevda.swoyotesttask.core.domain.vote.answer;

import com.github.evseevda.swoyotesttask.core.domain.exception.UserAlreadyVotedException;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class AnswersTest {

    private Answers answers;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        List<Answer> answerList = Arrays.asList(
                new Answer("Answer 1"),
                new Answer("Answer 2")
        );
        answers = new Answers(answerList);

        user1 = new User();
        user1.setUsername("user1");
        user2 = new User();
        user2.setUsername("user2");
    }

    @Test
    void givenAnswersList_WhenGetByPositionIsCalled_ThenReturnCorrectAnswer() {
        // arrange
        Answer expectedAnswer = new Answer("Answer 1");

        // action
        Answer actualAnswer = answers.getByPosition(1);

        // assertion
        Assertions.assertEquals(expectedAnswer.getAnswer(), actualAnswer.getAnswer());
    }

    @Test
    void givenUserAndAnswerPosition_WhenVoteIsCalled_ThenUserIsAddedToVoters() {
        // arrange
        int answerPosition = 1;

        // action
        answers.vote(user1, answerPosition);

        // assertion
        Assertions.assertEquals(1, answers.getByPosition(answerPosition).votersCount());
        Assertions.assertTrue(answers.getByPosition(answerPosition).getVotedUsers().contains(user1));
    }

    @Test
    void givenUserWhoAlreadyVoted_WhenVoteIsCalled_ThenThrowUserAlreadyVotedException() {
        // arrange
        answers.vote(user1, 1);

        // action & assertion
        Assertions.assertThrows(UserAlreadyVotedException.class, () -> answers.vote(user1, 2));
    }

    @Test
    void givenAnswersListWithVotes_WhenDefaultRepresentationIsCalled_ThenReturnCorrectString() {
        // arrange
        answers.vote(user1, 1);
        answers.vote(user2, 2);
        String expected = "1. Answer 1\t1\n2. Answer 2\t1\n";

        // action
        String result = answers.getRepresentator().defaultRepresentation();

        // assertion
        Assertions.assertEquals(expected, result);
    }

    @Test
    void givenAnswersListWithVotes_WhenGetLeaderAnswerIsCalled_ThenReturnAnswerWithMostVotes() {
        // arrange
        answers.vote(user1, 1);
        answers.vote(user2, 1);

        // action
        Answer leaderAnswer = answers.getAnalytics().getLeaderAnswer();

        // assertion
        Assertions.assertNotNull(leaderAnswer);
        Assertions.assertEquals("Answer 1", leaderAnswer.getAnswer());
    }

}