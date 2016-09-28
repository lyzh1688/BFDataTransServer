package com.beifeng.dtuProtocal;

import com.beifeng.util.DataTypeUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by 胡志洁 on 2016/8/19.
 */
public class DataMessage implements IMessage{

    private final byte bgnFlag = 0x7b;
    private byte type;
    private short msgLen = 16;
    private String msgBody;
    private final byte endFlag = 0x7b;
    private final int headLen = 16;

    public String getMsgBody() {
        return msgBody;
    }
    public DataMessage(byte[] msgBytes){
        this.type = msgBytes[1];
        byte[] sizeByte = new byte[2];
        System.arraycopy(msgBytes, 2, sizeByte, 0, 2);
        this.msgLen = (short) (DataTypeUtil.byteToInt(sizeByte));
        byte[] msgBodyBytes = new byte[this.msgLen - this.headLen];
        System.arraycopy(msgBytes,4,msgBodyBytes,0,this.msgLen - this.headLen);
        try {
            this.msgBody = new String(msgBodyBytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.msgBody = "";
        }

    }

    @Override
    public int getLength() {

        return this.msgLen;
    }
}
