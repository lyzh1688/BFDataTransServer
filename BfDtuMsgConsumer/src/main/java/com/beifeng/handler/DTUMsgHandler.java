package com.beifeng.handler;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 胡志洁 on 2016/6/22.
 */
public class DTUMsgHandler extends Thread  {

    public DTUMsgHandler(KafkaStream<String, String> stream, CountDownLatch latch) {
        this.stream = stream;
        this.latch = latch;
        this.dbHandler = new DBHandler();
    }



    private KafkaStream<String,String> stream;
    private DBHandler dbHandler;
    private CountDownLatch latch;

    @Override
    public void run() {

        System.out.println("Thread Begins : ");
        ConsumerIterator<String,String> streamIterator = this.stream.iterator();

        // 逐条处理消息
        while (streamIterator.hasNext()) {
            String msg = streamIterator.next().message();
            System.out.println(msg);
            boolean res = this.dbHandler.insert(msg);
            System.out.println("insert " + res);
        }

        this.latch.countDown();//工人完成工作，计数器减一
    }
}
