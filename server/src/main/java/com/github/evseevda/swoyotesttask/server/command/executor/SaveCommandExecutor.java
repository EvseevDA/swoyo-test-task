package com.github.evseevda.swoyotesttask.server.command.executor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.executor.ServerCommandExecutor;
import com.github.evseevda.swoyotesttask.server.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutionResult.ERROR;
import static com.github.evseevda.swoyotesttask.core.command.executor.CommandExecutionResult.OK;

@Component
@RequiredArgsConstructor
public class SaveCommandExecutor implements ServerCommandExecutor {

    private final TopicRepository topicRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String execute(Command command) {
        String filename = command.getArgs().get("f");
        Path filePath = Path.of(filename);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(objectMapper.writeValueAsString(topicRepository.getTopics()));
        } catch (Throwable e) {
            return ERROR.name();
        }
        return OK.name();
    }

    @Override
    public String command() {
        return "save";
    }

}
