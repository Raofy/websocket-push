package com.ryan.websocketpush;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebsocketPushApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsocketPushApplication.class, args);
    }

}
