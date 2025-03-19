package com.github.evseevda.swoyotesttask.server.repository;

import com.github.evseevda.swoyotesttask.core.domain.topic.Topics;

public interface TopicRepository {

    Topics getTopics();
    Topics setTopics(Topics topics);

}
