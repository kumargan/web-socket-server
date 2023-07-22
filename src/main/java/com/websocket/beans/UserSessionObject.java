package com.websocket.beans;

import com.websocket.beans.binary.ByteArrayWrapper;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Data
@Slf4j
@ToString
public class UserSessionObject {
    private Session session;
    private Queue<ByteArrayWrapper> queue = new ConcurrentLinkedQueue<>();
    private Queue<String> stringQueue = new ConcurrentLinkedQueue<>();

    public UserSessionObject(Session session){
        this.session = session;
    }

    @Override
    public boolean equals(Object o){
        UserSessionObject other = (UserSessionObject) o;
        if(other == null){
            return false;
        } else if(this.getClass() != other.getClass()){
            return false;
        } else if( !this.session.getId().equals(other.session.getId())){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(this.session.getId());
    }

}
