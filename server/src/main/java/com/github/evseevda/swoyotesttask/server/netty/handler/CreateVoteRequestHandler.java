package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topic;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.request.dto.CreateVoteRequestBody;
import com.github.evseevda.swoyotesttask.core.messaging.response.ResponseStatus;
import com.github.evseevda.swoyotesttask.core.messaging.response.ServerResponse;
import com.github.evseevda.swoyotesttask.server.exception.ServerException;
import com.github.evseevda.swoyotesttask.server.infrastructure.annotation.AuthenticationRequired;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import com.github.evseevda.swoyotesttask.server.security.context.SecurityContext;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateVoteRequestHandler implements RequestHandler {

    private final TopicRepository topicRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public RequestAction requestAction() {
        return RequestAction.CREATE_VOTE;
    }

    @Override
    @AuthenticationRequired
    public void handle(ServerRequest request, ChannelHandlerContext context) {
        try {
            User currentUser = SecurityContext.getCurrentUser();
            CreateVoteRequestBody requestBody = objectMapper.readValue(request.getBody(), CreateVoteRequestBody.class);
            Optional<Topic> topic = topicRepository.getTopics().getTopicByName(requestBody.getTopicName());
            if (topic.isPresent()) {
                Vote vote = requestBody.getVote();
                vote.setOwner(currentUser);
                topic.get().addVote(vote);
                writeSuccessResponse(context);
            } else {
                writeNotFoundResponse(context);
            }
        } catch (Throwable e) {
            handleError(context);
        }
    }

    private void writeSuccessResponse(ChannelHandlerContext context) throws JsonProcessingException {
        ServerResponse response = ServerResponse.ok(RequestAction.CREATE_VOTE, "CREATED");
        String responseString = objectMapper.writeValueAsString(response);
        context.writeAndFlush(responseString);
    }

    private void writeNotFoundResponse(ChannelHandlerContext context) throws JsonProcessingException {
        ServerResponse response = ServerResponse.builder()
                .action(RequestAction.CREATE_VOTE)
                .status(ResponseStatus.NOT_FOUND)
                .body(ResponseStatus.NOT_FOUND.name())
                .build();
        String stringResponse = objectMapper.writeValueAsString(response);
        context.writeAndFlush(stringResponse);
    }

    private void handleError(ChannelHandlerContext context) {
        ServerResponse response = ServerResponse.error(RequestAction.CREATE_VOTE, "Some error occurred.");
        String responseString = null;
        try {
            responseString = objectMapper.writeValueAsString(response);
            context.writeAndFlush(responseString);
        } catch (JsonProcessingException ex) {
            throw new ServerException("Server exception occurred.", ex);
        }
    }

}
