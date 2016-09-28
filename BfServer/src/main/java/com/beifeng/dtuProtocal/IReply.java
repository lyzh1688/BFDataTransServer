package com.beifeng.dtuProtocal;

/**
 * Created by 胡志洁 on 2016/8/19.
 */
public interface IReply {
    public byte[] toBytes();
    public int getLength();
}
