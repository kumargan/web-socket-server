package com.websocket.executor;

import com.websocket.beans.binary.ByteArrayWrapper;
import com.websocket.beans.binary.SampleDataBin;
import com.websocket.cache.UserSessionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Set;

@Slf4j
@Component
public class SampleDataGenerator implements Runnable{

    @Autowired
    UserSessionCache userSessionCache;

    Thread generatorThread;

    @PostConstruct
    public void init(){
        generatorThread = new Thread(this);
        generatorThread.setName("SampleDataGenerator Thread");
        generatorThread.start();
    }

    public void run() {
        log.info(" SampleDataGenerator thread started ");
        while(true) {
            try {
                try{Thread.sleep(2000);}catch(Exception e1){/* ignore thread interruption */}

                if (!CollectionUtils.isEmpty(userSessionCache.getUserSessionMap())) {
                    //Get all sessions  to avoid unintended starvation of any user session
                    Set<String> sessionKeySet = userSessionCache.getUserSessionMap().keySet();

//                for (String key : sessionKeySet) {
//                    userSessionCache.getUserSessionMap().get(key).getQueue().add(new ByteArrayWrapper(new SampleDataBin().toByteArray()));
//                }

                    for (String key : sessionKeySet) {
                        //log.info(" writing to queue {} ",key);
                        userSessionCache.getUserSessionMap().get(key).getStringQueue().add(" hi ");
                    }


                } else {
                    try {
                        Thread.sleep(2);
                    } catch (Exception e1) {/* ignore thread interruption */}
                }
            } catch (Exception e) {
                log.error(" exception in putting message to user queue {}", e);
            }
        }
    }
}
