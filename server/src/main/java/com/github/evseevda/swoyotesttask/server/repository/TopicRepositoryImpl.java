package com.github.evseevda.swoyotesttask.server.repository;

import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;
import org.springframework.stereotype.Component;

@Component
public class TopicRepositoryImpl implements TopicRepository {

    private Topics topics = new Topics();

    @Override
    public Topics getTopics() {
        return topics;
    }

    @Override
    public Topics setTopics(Topics topics) {
        return this.topics = topics;
    }

}
