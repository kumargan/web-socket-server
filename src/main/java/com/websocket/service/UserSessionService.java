package com.websocket.service;

import com.websocket.beans.binary.ErrorBin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.nio.ByteBuffer;

@Slf4j
@Component
public class UserSessionService {
    public void sendCustomResponse(Session session, ErrorBin errorBin) {
        session.getAsyncRemote().sendBinary(ByteBuffer.wrap(errorBin.toByteArray()));
    }

    public void handleUserMessage(Session session, byte[] message) {
        log.info(" user binary message {} ",message);
    }

    public void handleUserMessage(Session session, String message) {
        log.info(" user string message {} ",message);
    }

    public void handleDisconnet(Session session) {
        //log.info(" diconnecting session {} ",session.getId());
    }
}
