package com.beifeng;

import com.beifeng.config.KafkaProperties;
import com.beifeng.consumer.DtuConsumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 胡志洁 on 2016/6/22.
 */
public class main {

    public static void main(String[] args) throws InterruptedException {
       String groupid = "dtucomsumergroup";
       DtuConsumer consumer = new DtuConsumer(groupid);
       consumer.consume();
//        new main().consume();
    }

}
