package com.github.evseevda.swoyotesttask.core.domain.vote.answer;

import com.github.evseevda.swoyotesttask.core.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AnswerTest {

    @Test
    void givenAnswerWithNoVoters_WhenVotersCountIsCalled_ThenReturnZero() {
        // arrange
        Answer answer = new Answer("Test Answer");

        // action
        int votersCount = answer.votersCount();

        // assertion
        Assertions.assertEquals(0, votersCount);
    }

    @Test
    void givenAnswerWithOneVoter_WhenVotersCountIsCalled_ThenReturnOne() {
        // arrange
        Answer answer = new Answer("Test Answer");
        User user = new User();
        user.setUsername("user1");
        answer.addVoter(user);

        // action
        int votersCount = answer.votersCount();

        // assertion
        Assertions.assertEquals(1, votersCount);
    }

    @Test
    void givenAnswerWithMultipleVoters_WhenVotersCountIsCalled_ThenReturnCorrectCount() {
        // arrange
        Answer answer = new Answer("Test Answer");
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        answer.addVoter(user1);
        answer.addVoter(user2);

        // action
        int votersCount = answer.votersCount();

        // assertion
        Assertions.assertEquals(2, votersCount);
    }

    @Test
    void givenAnswer_WhenToStringIsCalled_ThenReturnCorrectString() {
        // arrange
        Answer answer = new Answer("Test Answer");

        // action
        String result = answer.toString();

        // assertion
        Assertions.assertEquals("Test Answer", result);
    }

}