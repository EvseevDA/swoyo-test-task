package com.github.evseevda.swoyotesttask.client;

import com.github.evseevda.swoyotesttask.client.netty.client.NettyClient;
import com.github.evseevda.swoyotesttask.core.config.CoreConfig;
import com.github.evseevda.swoyotesttask.core.config.InputOutputConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({CoreConfig.class, InputOutputConfig.class})
public class ClientApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
        NettyClient client = context.getBean(NettyClient.class);
        client.start();
    }

}
