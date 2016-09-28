package com.beifeng.handler;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by 胡志洁 on 2016/7/29.
 */
public class DTUMsg {

    public String getDtuId() {
        return dtuId;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getMsgFiled(int i) {

        if(i < this.msgBody.length && i >= 0)
            return this.msgBody[i];
        else
            return "0";
    }

    private final String dtuId;
    private final java.sql.Timestamp time;
    private final String type;
    private final String[] msgBody;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public DTUMsg(String dtuId, String time, String type, String[] msgBody) throws ParseException {

        this.dtuId = dtuId;
        this.time = new java.sql.Timestamp(df.parse(time).getTime());
        this.type = type;
        this.msgBody = msgBody;
    }
}
