package com.github.evseevda.swoyotesttask.core.domain.vote;

import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answer;
import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class VoteInfoTest {

    private VoteInfo voteInfo;

    @BeforeEach
    void setUp() {
        Answers answers = new Answers(Arrays.asList(
                new Answer("Answer 1"),
                new Answer("Answer 2")
        ));
        voteInfo = new VoteInfo("Test Vote Description", answers);
    }

    @Test
    void givenVoteInfo_WhenDefaultRepresentationIsCalled_ThenReturnCorrectString() {
        // arrange
        String expected = "Test Vote Description\n1. Answer 1\t0\n2. Answer 2\t0\n";

        // action
        String result = voteInfo.getRepresentator().defaultRepresentation();

        // assertion
        Assertions.assertEquals(expected, result);
    }

}