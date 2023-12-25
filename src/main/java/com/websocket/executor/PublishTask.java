package com.websocket.executor;

import com.websocket.beans.UserSessionObject;
import com.websocket.beans.binary.ByteArrayWrapper;
import com.websocket.constants.Constants;
import com.websocket.utils.ByteConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.websocket.Session;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;

@Slf4j
public class PublishTask implements Callable<Integer> {

    private final List<UserSessionObject> userSessions;

    public PublishTask(List<UserSessionObject> sessions){
        this.userSessions=sessions;
    }

    @Override
    public Integer call(){
        int packetSent = 0;
        try {
            for( UserSessionObject userSession : userSessions){
                try{
                    log.info("writing to user session, queue size {} ",userSession.getQueue().size());
                    writeFromByteQueue(userSession.getQueue(),userSession.getSession());
                    writeFromStringQueue(userSession.getStringQueue(),userSession.getSession());
                }catch(Exception e){
                    log.error(" Exception while processing session {}, error {}",userSession.getSession().getId(),e);
                }
            }
        }catch(Exception e1){
            log.error(" Exception in loop, fix the code {} ",e1);
        }
        return packetSent;
    }

    private void writeFromByteQueue(Queue<ByteArrayWrapper> messageQueue, Session session){

        //select only 10 packets from user session queue
        int currentQueueSize = Math.min(messageQueue.size(), Constants.MESSAGE_BULK_SIZE);
        List<ByteArrayWrapper> userPacketBytes = new ArrayList<>(currentQueueSize);
        //write 10 packets at a time
        while(currentQueueSize != 0){
            ByteArrayWrapper packet = messageQueue.poll();

            if(packet == null)
                break;

            userPacketBytes.add(packet);
            currentQueueSize--;
        }

        if(!userPacketBytes.isEmpty()){
            for(ByteArrayWrapper wrapper:userPacketBytes)
                session.getAsyncRemote().sendBinary(ByteBuffer.wrap(wrapper.getData()).order(ByteOrder.LITTLE_ENDIAN));
        }
    }

    private void writeFromStringQueue(Queue<String> messageQueue, Session session){
        //select only 10 packets from user session queue
        int currentQueueSize = Math.min(messageQueue.size(),Constants.MESSAGE_BULK_SIZE);

        //write 10 packets at a time
        while(currentQueueSize != 0){
            String packet = messageQueue.poll();

            if(packet == null || packet.length()==0) {
                log.info("Empty string packet for session {}",session.getId());
                break;
            }

            session.getAsyncRemote().sendText(packet);
            currentQueueSize--;
        }
    }
}
