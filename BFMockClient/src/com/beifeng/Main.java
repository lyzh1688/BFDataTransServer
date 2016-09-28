package com.beifeng;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
	    // write your code here
//        for(int i = 0 ; i < 10 ; ++i){
//            MockClient.CreateClient();
//        }
        MockClient.CreateClient();
    }
}
