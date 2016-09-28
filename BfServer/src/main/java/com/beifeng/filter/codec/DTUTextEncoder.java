package com.beifeng.filter.codec;

import com.beifeng.dtuProtocal.IReply;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * Created by 胡志洁 on 2016/6/20.
 */
public class DTUTextEncoder extends ProtocolEncoderAdapter {
    public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        if(message instanceof IReply){
            IReply msg = (IReply)message;
            IoBuffer buff = IoBuffer.allocate(msg.getLength());
            buff.setAutoExpand(true);
            buff.put(msg.toBytes());
            buff.flip();
            protocolEncoderOutput.write(buff);
        }
    }
}
