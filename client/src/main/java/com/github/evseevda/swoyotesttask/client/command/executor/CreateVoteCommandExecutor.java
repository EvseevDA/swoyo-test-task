package com.github.evseevda.swoyotesttask.client.command.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import com.github.evseevda.swoyotesttask.core.domain.vote.VoteInfo;
import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answer;
import com.github.evseevda.swoyotesttask.core.domain.vote.answer.Answers;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.request.dto.CreateVoteRequestBody;
import com.github.evseevda.swoyotesttask.core.ui.input.UserInputReader;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateVoteCommandExecutor extends ExceptionHandlingClientCommandExecutor {

    private final Channel channel;

    private final UserInputReader userInputReader;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected String logic(Command command) throws Exception {
        String topic = command.getArgs().get("t");
        String requestBody = makeRequestBody(topic);
        makeRequestAndSend(requestBody);
        return "sent";
    }

    private void makeRequestAndSend(String requestBody) throws JsonProcessingException {
        ServerRequest serverRequest = ServerRequest.builder()
                .action(RequestAction.CREATE_VOTE)
                .body(requestBody)
                .build();
        String requestString = objectMapper.writeValueAsString(serverRequest);
        channel.writeAndFlush(requestString + System.lineSeparator());
    }

    private String makeRequestBody(String topic) throws JsonProcessingException {
        String name = askUserForName();
        String description = askUserForDescription();
        Answers answers = askUserForAnswers();

        CreateVoteRequestBody requestBody = CreateVoteRequestBody.builder()
                .topicName(topic)
                .vote(Vote.builder()
                        .name(name)
                        .info(VoteInfo.builder()
                                .description(description)
                                .answers(answers)
                                .build())
                        .build())
                .build();

        return objectMapper.writeValueAsString(requestBody);
    }

    private String askUserForName() {
        return userInputReader.readString("Название голосования:");
    }

    private String askUserForDescription() {
        return userInputReader.readString("Тема голосования:");
    }

    private Answers askUserForAnswers() {
        int answersCount = askUserForAnswersCount();
        List<Answer> answers = askUserForAnswers(answersCount);
        return new Answers(answers);
    }

    private int askUserForAnswersCount() {
        return userInputReader.readInt("Кол-во ответов:");
    }

    private List<Answer> askUserForAnswers(int answersCount) {
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < answersCount; i++) {
            answers.add(askUserForAnswer(i + 1));
        }
        return answers;
    }

    private Answer askUserForAnswer(int answerNumber) {
        String answer = userInputReader.readString("Ответ #" + answerNumber + ":");
        return new Answer(answer);
    }

    @Override
    public String command() {
        return "create vote";
    }
}
