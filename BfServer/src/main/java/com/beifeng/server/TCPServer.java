package com.beifeng.server;


import com.beifeng.filter.codec.DTUTextCodecFactory;
import com.beifeng.handler.DTUHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by 胡志洁 on 2016/6/12.
 */
public class TCPServer {

    private static final int PORT = 9123;

    public TCPServer() {
        IoAcceptor acceptor = new NioSocketAcceptor();

        acceptor.getFilterChain().addLast("logger",new LoggingFilter());
        //acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.getFilterChain().addLast("dtucodec",new ProtocolCodecFilter(new DTUTextCodecFactory()));

        acceptor.setHandler(  new DTUHandler() );
        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );

        try {
            // 服务器开始监听
            acceptor.bind( new InetSocketAddress(PORT) );
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
