package com.beifeng.dtuProtocal;

import java.nio.ByteBuffer;

/**
 * Created by 胡志洁 on 2016/8/19.
 */
public class RegisterReply implements IReply {
    private final byte bgnFlag = 0x7b;
    private byte type;
    private final short msgLen = 16;
    private String id;
    private final byte endFlag = 0x7b;

    public RegisterReply(TypeEnum type,String id){
        this.type = DType.TypeMap.get(type);
        this.id = id;
    }


    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getLength(){
        return this.msgLen;
    }

    @Override
    public byte[] toBytes() {
        ByteBuffer buff = ByteBuffer.allocate(this.msgLen);
        buff.put(this.bgnFlag);
        buff.put(this.type);
        buff.putShort(this.msgLen);
        buff.put(this.id.getBytes());
        buff.put(this.endFlag);
        return buff.array();
    }
}
