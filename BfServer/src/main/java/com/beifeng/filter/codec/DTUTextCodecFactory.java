package com.beifeng.filter.codec;

import com.beifeng.dtuProtocal.RegisterReply;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by 胡志洁 on 2016/6/20.
 */
public class DTUTextCodecFactory implements ProtocolCodecFactory {

    private ProtocolEncoder encoder = null;

    private ProtocolDecoder decoder = null;

    public DTUTextCodecFactory() {

        this.encoder = new DTUTextEncoder();

        this.decoder = new DTUTextDecoder();

    }

    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return this.encoder;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return this.decoder;
    }
}
