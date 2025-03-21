package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.core.domain.vote.VoteInfo;
import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answers;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.request.dto.DoVoteRequestBody;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import com.github.evseevda.swoyotesttask.server.security.context.SecurityContext;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

class DoVoteRequestHandlerTest {

    private TopicRepository topicRepository;
    private DoVoteRequestHandler handler;
    private ChannelHandlerContext context;

    @BeforeEach
    void setUp() {
        topicRepository = Mockito.mock(TopicRepository.class);
        handler = new DoVoteRequestHandler(topicRepository);
        context = Mockito.mock(ChannelHandlerContext.class);
    }

    @Test
    void givenValidRequest_WhenHandleIsCalled_ThenVoteAndWriteResponse() throws Exception {
        // arrange
        User currentUser = new User("user");
        SecurityContext.setCurrentUser(currentUser);

        DoVoteRequestBody requestBody = new DoVoteRequestBody();
        requestBody.setTopic("Topic");
        requestBody.setVote("Vote");
        requestBody.setAnswerPosition(1);

        ServerRequest request = new ServerRequest();
        request.setBody(new ObjectMapper().writeValueAsString(requestBody));

        Topics topics = Mockito.mock(Topics.class);
        Vote vote = Mockito.mock(Vote.class);
        VoteInfo voteInfo = Mockito.mock(VoteInfo.class);
        Answers answers = Mockito.mock(Answers.class);
        when(vote.getInfo()).thenReturn(voteInfo);
        when(voteInfo.getAnswers()).thenReturn(answers);
        when(topicRepository.getTopics()).thenReturn(topics);
        when(topics.getVote("Topic", "Vote")).thenReturn(Optional.of(vote));

        // action
        handler.handle(request, context);

        // assertion
        verify(vote.getInfo().getAnswers(), times(1)).vote(currentUser, 1);
        verify(context, times(1)).writeAndFlush(anyString());
    }

    @Test
    void givenVoteNotFound_WhenHandleIsCalled_ThenWriteNotFoundResponse() throws Exception {
        // arrange
        User currentUser = new User("user");
        SecurityContext.setCurrentUser(currentUser);

        DoVoteRequestBody requestBody = new DoVoteRequestBody();
        requestBody.setTopic("Topic");
        requestBody.setVote("Vote");
        requestBody.setAnswerPosition(1);

        ServerRequest request = new ServerRequest();
        request.setBody(new ObjectMapper().writeValueAsString(requestBody));

        Topics topics = Mockito.mock(Topics.class);
        when(topicRepository.getTopics()).thenReturn(topics);
        when(topics.getVote("Topic", "Vote")).thenReturn(Optional.empty());

        // action
        handler.handle(request, context);

        // assertion
        verify(context, times(1)).writeAndFlush(anyString());
    }

}