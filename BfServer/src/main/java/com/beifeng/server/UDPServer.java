package com.beifeng.server;

import com.beifeng.filter.codec.DTUTextCodecFactory;
import com.beifeng.filter.codec.DTUTextDecoder;
import com.beifeng.handler.DTUHandler;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by 胡志洁 on 2016/6/12.
 */
public class UDPServer {

    private static final long serialVersionUID = 1L;

    public static final int PORT = 9124;

    public UDPServer() throws IOException {

        NioDatagramAcceptor acceptor = new NioDatagramAcceptor();//创建一个UDP的接收器


        Executor threadPool = Executors.newFixedThreadPool(1);//建立线程池
        acceptor.getFilterChain().addLast("pack",new ProtocolCodecFilter(new DTUTextCodecFactory()));
        acceptor.getFilterChain().addLast("exector", new ExecutorFilter(threadPool));
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        //acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new DTUHandler());//设置接收器的处理程序
        DatagramSessionConfig dcfg = acceptor.getSessionConfig();//建立连接的配置文件
        dcfg.setReadBufferSize(4096);//设置接收最大字节默认2048
        dcfg.setReceiveBufferSize(2048);//设置输入缓冲区的大小
        dcfg.setSendBufferSize(2048);//设置输出缓冲区的大小
        dcfg.setReuseAddress(true);//设置每一个非主监听连接的端口可以重用

        acceptor.bind(new InetSocketAddress(PORT));//绑定端口

//      NioDatagramAcceptor acceptor = new NioDatagramAcceptor();
//      acceptor.setHandler(new DTUHandler());

//      Executor threadPool = Executors.newCachedThreadPool();
//      DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
//      chain.addLast("logger", new LoggingFilter());
//      chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
//      chain.addLast("threadPool", new ExecutorFilter(threadPool));
//
//      DatagramSessionConfig dcfg = acceptor.getSessionConfig();
//      dcfg.setReadBufferSize(4096);// 设置接收最大字节默认2048
//      dcfg.setMaxReadBufferSize(65536);
//      dcfg.setReceiveBufferSize(1024);// 设置输入缓冲区的大小
//      dcfg.setSendBufferSize(1024);// 设置输出缓冲区的大小
//      dcfg.setReuseAddress(true);// 设置每一个非主监听连接的端口可以重用
//
//      acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("UDPServer listening on port " + PORT);
    }
}
