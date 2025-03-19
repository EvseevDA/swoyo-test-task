package com.github.evseevda.swoyotesttask.core;

import com.github.evseevda.swoyotesttask.core.command.Command;
import com.github.evseevda.swoyotesttask.core.command.parser.CommandParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
//        Topic topic = Topic.builder()
//                .name("Астрономия")
//                .build();
//
//        User user = new User("Dobryak2004");
//        User user1 = new User("Zlodey2005");
//
//        Vote vote = Vote.builder()
//                .name("Количество звезд на небе 1")
//                .topic(topic)
//                .owner(user)
//                .info(
//                        VoteInfo.builder()
//                                .description("Сколько звезд на небе?")
//                                .answers(
//                                        Answers.builder()
//                                                .answers(
//                                                        List.of(
//                                                                Answer.builder()
//                                                                        .answer("Много")
//                                                                        .build(),
//                                                                Answer.builder()
//                                                                        .answer("Очень много")
//                                                                        .build()
//                                                        )
//                                                )
//                                                .build()
//                                )
//                                .build()
//                )
//                .build();
//        Vote vote1 = Vote.builder()
//                .name("Количество звезд на небе 2")
//                .topic(topic)
//                .owner(user)
//                .info(
//                        VoteInfo.builder()
//                                .description("Сколько звезд на небе?")
//                                .answers(
//                                        Answers.builder()
//                                                .answers(
//                                                        List.of(
//                                                                Answer.builder()
//                                                                        .answer("Много")
//                                                                        .build(),
//                                                                Answer.builder()
//                                                                        .answer("Очень много")
//                                                                        .build()
//                                                        )
//                                                )
//                                                .build()
//                                )
//                                .build()
//                )
//                .build();
//
//        vote.vote(user, 1);
//        vote.vote(user1, 2);
//        System.out.println(vote);
//        System.out.println(vote.getAnalytics().getLeaderAnswer());
//        System.out.println(topic.getRepresentator().detailedRepresentation());
        ApplicationContext context = new AnnotationConfigApplicationContext("com");
        CommandParser commandParser = context.getBean(CommandParser.class);
        Command command = commandParser.parse("view -v=Кто построил Египетские пирамиды -t=Загадки человечества -p=DETAILED ANSWER");
        System.out.println(command);

    }

}
