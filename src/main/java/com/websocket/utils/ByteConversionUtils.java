package com.websocket.utils;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

@Slf4j
public class ByteConversionUtils {

    public static ByteBuffer convertToBuffer(List<Byte> byteArrayList){

        if(byteArrayList.isEmpty()){
            return null;
        }

        byte[] packet = new byte[byteArrayList.size()];

        for(int i=0;i<byteArrayList.size();i++){
            packet[i] = byteArrayList.get(i);
        }

        return ByteBuffer.wrap(packet).order(ByteOrder.LITTLE_ENDIAN);
    }

}
