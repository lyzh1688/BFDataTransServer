package com.beifeng;

import com.beifeng.server.TCPServer;
import com.beifeng.server.UDPServer;

import java.io.IOException;


/**
 * Created by kfzx-liuyz1 on 2016/5/20.
 */
public class BFServer {

    public static void main(String[] agrs) throws IOException {
        //new UDPServer();
        new TCPServer();
    }
}
