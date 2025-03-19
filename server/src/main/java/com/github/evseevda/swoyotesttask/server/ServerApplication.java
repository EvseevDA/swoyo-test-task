package com.github.evseevda.swoyotesttask.server;

import com.github.evseevda.swoyotesttask.core.config.CoreConfig;
import com.github.evseevda.swoyotesttask.core.config.InputOutputConfig;
import com.github.evseevda.swoyotesttask.server.netty.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CoreConfig.class, InputOutputConfig.class})
public class ServerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);
        NettyServer server = context.getBean(NettyServer.class);
        server.start();
    }

}
