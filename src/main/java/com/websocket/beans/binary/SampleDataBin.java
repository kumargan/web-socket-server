package com.websocket.beans.binary;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class SampleDataBin {
    public static final int packetLength=8;
    String msg = "abcedefg";

    public List<Byte> toByteList(){
        ByteBuffer buffer = ByteBuffer.allocate(packetLength).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(msg.getBytes()).rewind();
        List<Byte>  res= new ArrayList<>();
        for(byte b: buffer.array())
            res.add(b);

        return res;
    }
    public byte[] toByteArray(){
        ByteBuffer buffer = ByteBuffer.allocate(packetLength).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(msg.getBytes()).rewind();
        return buffer.array();
    }

}
