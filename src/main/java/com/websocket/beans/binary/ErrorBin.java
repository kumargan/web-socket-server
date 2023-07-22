package com.websocket.beans.binary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorBin {
    public static int packetLength = 100;

    @JsonProperty("msg")
    private String message;

    public List<Byte> toByteList(){
        ByteBuffer buffer = ByteBuffer.allocate(packetLength).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(message.getBytes());
        List<Byte>  res= new ArrayList<>();
        for(byte b: buffer.array())
            res.add(b);

        return res;
    }
    public byte[] toByteArray(){
        ByteBuffer buffer = ByteBuffer.allocate(packetLength).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(message.getBytes());
        return buffer.array();
    }

}
