package com.websocket.cache;

import com.websocket.beans.UserSessionObject;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class UserSessionCache {
    public static Map<String, UserSessionObject> userSessionMap = new ConcurrentHashMap<>();

    public static Map<String, UserSessionObject> getUserSessionMap(){
        return userSessionMap;
    }

}
