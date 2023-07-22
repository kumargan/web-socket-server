package com.websocket.listener;

import com.websocket.beans.UserSessionObject;
import com.websocket.beans.binary.ErrorBin;
import com.websocket.cache.UserSessionCache;
import com.websocket.config.CustomSpringConfigurator;
import com.websocket.constants.Constants;
import com.websocket.service.UserSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Slf4j
@ServerEndpoint(value= Constants.ENDPOINT_AUTH_V1,configurator = CustomSpringConfigurator.class)
public class WebsocketServerListnerEndpointV1 {


    @Value("${user.connection.limit.per.server}")
    private Integer userConnectionLimit;

    @Value("${user.connection.threshold.time}")
    private Long userThresholdTime;

    UserSessionService userSessionService = new UserSessionService();


    @OnOpen
    public void onOpen(Session session){
        UserSessionObject sessionObject = new UserSessionObject(session);
        UserSessionCache.userSessionMap.put(session.getId(),sessionObject);

        log.info(" session opened, total sessions {} ",UserSessionCache.userSessionMap.size());
    }

    @OnMessage
    public void onMessage(Session session, byte[] message){
        try{
            if(message.length > Constants.MAX_USER_MESSAGE_SIZE){
                userSessionService.sendCustomResponse(session, new ErrorBin(Constants.MAX_USER_MSG_LEN_EXCEEDED));
                return;
            }
            userSessionService.handleUserMessage(session,message);
        }catch(Exception e){
            log.error(" Exception while processing user message {}",e);
            userSessionService.sendCustomResponse(session, new ErrorBin(Constants.GENERIC_EXCEPTION_MSG));
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            userSessionService.handleUserMessage(session, message);
        } catch (Exception e) {
            log.error(" Exception while processing user message {}", e);
            userSessionService.sendCustomResponse(session, new ErrorBin(Constants.GENERIC_EXCEPTION_MSG));
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason){
        UserSessionCache.userSessionMap.remove(session.getId());
        userSessionService.handleDisconnet(session);
        log.info(" closed session, total sessions {} , reason {}, code ",UserSessionCache.userSessionMap.size(), reason.getReasonPhrase(), reason.getCloseCode());
    }

    @OnError
    public void onError(Session session, Throwable t){
        UserSessionCache.userSessionMap.remove(session.getId());
        log.error(" Exception occurred in session {} err {}",session.getId(), t);
    }
}

