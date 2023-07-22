package com.websocket.config;

import com.websocket.listener.WebsocketServerListnerEndpointV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {


    @Bean
    public ServerEndpointExporter endpointExporter(){
        return new ServerEndpointExporter();
    }

    @Bean
    public WebsocketServerListnerEndpointV1 getServerEndpointV1(){
        return new WebsocketServerListnerEndpointV1();
    }
}
