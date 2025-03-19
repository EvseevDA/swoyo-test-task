package com.github.evseevda.swoyotesttask.core.domain.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.evseevda.swoyotesttask.core.domain.exception.TopicAlreadyExistsException;
import com.github.evseevda.swoyotesttask.core.domain.vote.Vote;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
public class Topics {

    private final List<Topic> topics = new ArrayList<>();

    @JsonIgnore
    private final Representator representator = new Representator();

    public void addTopic(Topic topic) {
        requireTopicNotExists(topic);
        topics.add(topic);
    }

    public Optional<Topic> getTopicByName(String topicName) {
        for (Topic topic : topics) {
            if (topic.getName().equals(topicName)) {
                return Optional.of(topic);
            }
        }
        return Optional.empty();
    }

    public Optional<Vote> getVote(String topicName, String voteName) {
        Optional<Topic> optionalTopic = getTopicByName(topicName);
        if (optionalTopic.isEmpty()) {
            return Optional.empty();
        } else {
            Topic topic = optionalTopic.get();
            return topic.getVoteByName(voteName)
                    .or(Optional::empty);
        }
    }

    public void deleteVote(String topicName, String voteName) {
        Optional<Topic> topicByName = getTopicByName(topicName);
        if (topicByName.isPresent()) {
            Topic topic = topicByName.get();
            topic.getVotes().deleteVote(voteName);
        }
    }

    private void requireTopicNotExists(Topic topic) {
        if (topics.contains(topic)) {
            throw new TopicAlreadyExistsException(
                    "Topic '%s' already exists."
                            .formatted(topic.getName())
            );
        }
    }

    @Override
    public String toString() {
        return representator.defaultRepresentation();
    }

    public class Representator {
        public String defaultRepresentation() {
            StringBuilder sb = new StringBuilder();
            for (Topic topic : topics) {
                sb.append(topic.getRepresentator().defaultRepresentation()).append('\n');
            }
            return sb.toString();
        }
    }

}
