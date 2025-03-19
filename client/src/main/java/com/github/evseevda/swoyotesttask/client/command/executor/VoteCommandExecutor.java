package com.github.evseevda.swoyotesttask.client.command.executor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ClientCommandExecutor;
import com.github.evseevda.swoyotesttask.core.messaging.request.RequestAction;
import com.github.evseevda.swoyotesttask.core.messaging.request.ServerRequest;
import com.github.evseevda.swoyotesttask.core.messaging.request.dto.DoVoteRequestBody;
import com.github.evseevda.swoyotesttask.core.ui.input.UserInputReader;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class VoteCommandExecutor extends ExceptionHandlingClientCommandExecutor {

    private final Channel channel;
    private final ClientCommandExecutor viewCommandExecutor;
    private final UserInputReader userInputReader;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected String logic(Command command) throws Exception {
        viewVote(command);
        Thread.sleep(1000);
        int answer = askUserForAnswer();
        String requestBody = makeRequestBody(command, answer);
        makeRequestAndSend(requestBody);
        return "sent";
    }

    protected void viewVote(Command command) {
        Command viewCommand = new Command(viewCommandExecutor.command(), command.getArgs());
        viewCommandExecutor.execute(viewCommand);
    }

    private int askUserForAnswer() {
        return userInputReader.readInt("Введите номер ответа:");
    }

    private String makeRequestBody(Command command, int answer) throws JsonProcessingException {
        Map<String, String> args = command.getArgs();
        String topic = args.get("t");
        String vote = args.get("v");
        DoVoteRequestBody requestBody = DoVoteRequestBody.builder()
                .topic(topic)
                .vote(vote)
                .answerPosition(answer)
                .build();
        return objectMapper.writeValueAsString(requestBody);
    }

    private void makeRequestAndSend(String requestBody) throws JsonProcessingException {
        ServerRequest serverRequest = ServerRequest.builder()
                .action(RequestAction.DO_VOTE)
                .body(requestBody)
                .build();
        channel.writeAndFlush(objectMapper.writeValueAsString(serverRequest) + System.lineSeparator());
    }

    @Override
    public String command() {
        return "vote";
    }
}
