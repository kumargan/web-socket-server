package com.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com" })
@Slf4j
public class WebsocketMuxApp {
    public static void main(String[] args) {
        SpringApplication.run(WebsocketMuxApp.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void afterStartup() {
        log.info("*************** started application *****************");
    }
}