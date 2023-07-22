package com.websocket.executor;

import com.websocket.beans.UserSessionObject;
import com.websocket.cache.UserSessionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

@Component
@Slf4j
public class UserMessagePublisher implements Runnable{

    @Autowired
    UserSessionCache userSessionCache;

    @Autowired
    PublisherExecutorUtil publisherExecutorUtil;

    Thread publisherThread;
    Integer publishSessionBatchSize=100;

    @PostConstruct
    public void init(){
        publisherThread = new Thread(this);
        publisherThread.setName("Publisher Thread");
        publisherThread.start();
    }

    public void run(){
        log.info(" Started UserMessagePublisher job");

        while(true){
            try{
                List<Future<Integer>> publisherFutureList = new ArrayList<>();
                //log.info(" user session cache  {}",userSessionCache.getUserSessionMap().size());
                if(!CollectionUtils.isEmpty(userSessionCache.getUserSessionMap())){

                    //Get all sessions  to avoid unintended starvation of any user session
                    Set<String> sessionKeySet = userSessionCache.getUserSessionMap().keySet();

                    //Create fixed group of 100 session for each thread to keep thread switching minimum
                    List<UserSessionObject> userSessions = new ArrayList<>(publishSessionBatchSize);

                    for(String key:sessionKeySet){
                        if(!userSessionCache.getUserSessionMap().get(key).getSession().isOpen()){
                            log.info(" removing phantom session {} ",userSessionCache.getUserSessionMap().get(key).getSession().getId());
                            userSessionCache.getUserSessionMap().remove(key);
                            break;
                        }
                        if(!userSessionCache.getUserSessionMap().get(key).getQueue().isEmpty() ||
                                !userSessionCache.getUserSessionMap().get(key).getStringQueue().isEmpty())
                                {

                            userSessions.add(userSessionCache.getUserSessionMap().get(key));
                        }
                        //submit to executor pool, save the future object and refresh array list
                        if(userSessions.size() == publishSessionBatchSize){
                            publisherFutureList.add(publisherExecutorUtil.checkAndSubmit(new PublishTask(userSessions)));
                            userSessions = new ArrayList<>(publishSessionBatchSize);
                        }
                    }

                    //submit last iteration of batching
                    if(!userSessions.isEmpty())
                        publisherFutureList.add(publisherExecutorUtil.checkAndSubmit(new PublishTask(userSessions)));

                    //Wait for all sessions to finish
                    for(Future<Integer> future:publisherFutureList){
                        try{
                            future.get();
                        }catch(Exception e2){
                            log.error(" Exception while waiting for future {}",e2);
                        }
                    }

                }else{
                    try{Thread.sleep(200);}catch(Exception e1){/* ignore thread interruption */}
                }

            }catch(Exception e){
                log.info("Exception not Expected. Exception while publishing packets to user session {}",e);
            }
        }
    }
}
