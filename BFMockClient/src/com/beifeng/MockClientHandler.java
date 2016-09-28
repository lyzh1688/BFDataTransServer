package com.beifeng;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Timer;

/**
 * Created by kfzx-liuyz1 on 2016/5/20.
 */
public class MockClientHandler extends IoHandlerAdapter {
    // 当客户端连接进入时
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("incomming 客户端: " + session.getRemoteAddress());
        session.write("i am coming");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        System.out.println("客户端发送信息异常....");
    }

    // 当客户端发送消息到达时
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {

        System.out.println("服务器返回的数据：" + message.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("客户端与服务端断开连接.....");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("one Client Connection" + session.getRemoteAddress());
        Timer timer = new Timer();
        MockDataTask mockDataTask = new MockDataTask(session);
        timer.schedule(mockDataTask, 1000, 2000);  //任务1 一秒钟后执行，每两秒执行一次。

        //session.write("我来了······");
    }
}
