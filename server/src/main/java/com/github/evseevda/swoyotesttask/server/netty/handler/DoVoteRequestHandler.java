package com.github.evseevda.swoyotesttask.server.netty.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import com.github.evseevda.swoyotesttask.core.domain.user.User;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.request.dto.DoVoteRequestBody;
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
public class DoVoteRequestHandler implements RequestHandler {

    private final TopicRepository topicRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public RequestAction requestAction() {
        return RequestAction.DO_VOTE;
    }

    @Override
    @AuthenticationRequired
    public void handle(ServerRequest request, ChannelHandlerContext context) {
        try {
            User currentUser = SecurityContext.getCurrentUser();
            Topics topics = topicRepository.getTopics();
            DoVoteRequestBody body = objectMapper.readValue(request.getBody(), DoVoteRequestBody.class);
            String topicName = body.getTopic();
            String voteName = body.getVote();
            int answerPosition = body.getAnswerPosition();

            Optional<Vote> vote = topics.getVote(topicName, voteName);
            if (vote.isPresent()) {
                vote.get().getInfo().getAnswers().vote(currentUser, answerPosition);
                writeSuccessResponse(context);
            } else {
                writeNotFoundResponse(context);
            }
        } catch (Throwable e) {
            handleError(context);
        }
    }

    private void writeNotFoundResponse(ChannelHandlerContext context) throws JsonProcessingException {
        ServerResponse response = ServerResponse.builder()
                .action(RequestAction.DO_VOTE)
                .status(ResponseStatus.NOT_FOUND)
                .body(ResponseStatus.NOT_FOUND.name())
                .build();
        String responseString = objectMapper.writeValueAsString(response);
        context.writeAndFlush(responseString);
    }

    private void writeSuccessResponse(ChannelHandlerContext context) throws JsonProcessingException {
        ServerResponse response = ServerResponse.ok(RequestAction.DO_VOTE, ResponseStatus.OK.name());
        String responseString = objectMapper.writeValueAsString(response);
        context.writeAndFlush(responseString);
    }

    private void handleError(ChannelHandlerContext context) {
        ServerResponse response = ServerResponse.error(RequestAction.DO_VOTE, "Some error occurred.");
        String responseString = null;
        try {
            responseString = objectMapper.writeValueAsString(response);
            context.writeAndFlush(responseString);
        } catch (JsonProcessingException ex) {
            throw new ServerException("Server exception occurred.", ex);
        }
    }
}
