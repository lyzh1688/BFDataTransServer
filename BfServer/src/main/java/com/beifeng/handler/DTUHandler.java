package com.beifeng.handler;

import com.beifeng.dtuProtocal.DType;
import com.beifeng.dtuProtocal.DataMessage;
import com.beifeng.dtuProtocal.RegisterReply;
import com.beifeng.dtuProtocal.TypeEnum;
import com.beifeng.kafka.producer.Configure;
import com.beifeng.kafka.producer.DtuMsgProducer;
import com.beifeng.redis.RedisUtil;
import com.beifeng.util.DataTypeUtil;
import com.sun.prism.PixelFormat;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.net.InetSocketAddress;
import java.util.Date;


/**
 * Created by kfzx-liuyz1 on 2016/5/20.
 */
public class DTUHandler extends IoHandlerAdapter {

    private DtuMsgProducer producer;

    private static int count = 0;

    public DTUHandler(){
        super();
        this.producer = new DtuMsgProducer();
    }
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        String clientIP = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
        session.setAttribute("KEY_SESSION_CLIENT_IP", clientIP);
        session.setAttribute("KEY_SESSION_INDEX", DTUHandler.count++);
        //logger.debug("sessionCreated, client IP: " + clientIP);
    }
    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        cause.printStackTrace();
    }

    /*
     * 这个方法是目前这个类里最主要的，
     * 当接收到消息，只要不是quit，就把服务器当前的时间返回给客户端
     * 如果是quit，则关闭客户端连接*/
    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        //String recvMsg = DataTypeUtil.ioBufferToString(message);

        //byte[] recvMsg = (String)message;
        byte[] recvBytes = (byte[])message;
        if (recvBytes[1] == DType.TypeMap.get(TypeEnum.REG)) {
            byte[] idBytes = new byte[11];
            System.arraycopy(recvBytes, 4, idBytes, 0, 11);

            RegisterReply msg = new RegisterReply(TypeEnum.ACK,new String(idBytes,"UTF-8"));

            session.write(msg);
        }
        else if(recvBytes[1] == DType.TypeMap.get(TypeEnum.MSG)) {
            Date date = new Date();
            String dataMessage = (new DataMessage(recvBytes)).getMsgBody();
            String writeMsg  = date.toString() + " : " + dataMessage;
            System.out.println(writeMsg);
            RedisUtil.getJedis().lpush(Configure.KAFKA_TOPIC,writeMsg);
            System.out.println("Message written to reids...");

            String index = String.valueOf(session.getAttribute("KEY_SESSION_INDEX"));

            this.producer.produce(index,dataMessage);
            System.out.println(dataMessage);
            System.out.println("Message written to kafka...");
        }


    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        super.sessionClosed(session);
        System.out.println("disconnect from client.....");
    }
}
