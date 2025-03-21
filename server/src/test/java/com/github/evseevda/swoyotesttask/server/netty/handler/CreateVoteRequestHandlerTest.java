package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topic;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.request.dto.CreateVoteRequestBody;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import com.github.evseevda.swoyotesttask.server.security.context.SecurityContext;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CreateVoteRequestHandlerTest {

    private TopicRepository topicRepository;
    private CreateVoteRequestHandler handler;
    private ChannelHandlerContext context;
    private Topics topics;
    private Topic topic;

    @BeforeEach
    void setUp() {
        topicRepository = Mockito.mock(TopicRepository.class);
        handler = new CreateVoteRequestHandler(topicRepository);
        context = Mockito.mock(ChannelHandlerContext.class);
        topics = Mockito.mock(Topics.class);
        topic = Mockito.mock(Topic.class);
        when(topicRepository.getTopics()).thenReturn(topics);
    }

    @Test
    void givenValidRequest_WhenHandleIsCalled_ThenAddVoteAndWriteResponse() throws Exception {
        // arrange
        User currentUser = new User("user");
        SecurityContext.setCurrentUser(currentUser);

        CreateVoteRequestBody requestBody = new CreateVoteRequestBody();
        requestBody.setTopicName("Topic");
        requestBody.setVote(new Vote());

        ServerRequest request = new ServerRequest();
        request.setBody(new ObjectMapper().writeValueAsString(requestBody));

        when(topicRepository.getTopics().getTopicByName("Topic")).thenReturn(Optional.of(topic));

        // action
        handler.handle(request, context);

        // assertion
        verify(topic, times(1)).addVote(any(Vote.class));
        verify(context, times(1)).writeAndFlush(anyString());
    }

    @Test
    void givenTopicNotFound_WhenHandleIsCalled_ThenWriteNotFoundResponse() throws Exception {
        // arrange
        User currentUser = new User("user");
        SecurityContext.setCurrentUser(currentUser);

        CreateVoteRequestBody requestBody = new CreateVoteRequestBody();
        requestBody.setTopicName("Topic");
        requestBody.setVote(new Vote());

        ServerRequest request = new ServerRequest();
        request.setBody(new ObjectMapper().writeValueAsString(requestBody));

        when(topicRepository.getTopics().getTopicByName("Topic")).thenReturn(Optional.empty());

        // action
        handler.handle(request, context);

        // assertion
        verify(context, times(1)).writeAndFlush(anyString());
    }

}