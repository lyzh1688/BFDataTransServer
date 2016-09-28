package com.beifeng.consumer;

import com.beifeng.config.KafkaProperties;
import com.beifeng.handler.DTUMsgHandler;
import kafka.consumer.Consumer;
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
import java.util.concurrent.CountDownLatch;

/**
 * Created by 胡志洁 on 2016/6/22.
 */
public class DtuConsumer {

    private final ConsumerConnector consumer;

    private CountDownLatch latch;

    public DtuConsumer(String groupid){
        super();

        this.latch = new CountDownLatch(1);

        Properties props = new Properties();
        //zookeeper 配置
        props.put("zookeeper.connect", KafkaProperties.ZK_CONNECT);

        //group 代表一个消费组
        props.put("group.id", groupid);

        //zk连接超时
        props.put("zookeeper.session.timeout.ms", "30000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        //序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        ConsumerConfig config = new ConsumerConfig(props);

        this.consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);

    }

    public void consume() throws InterruptedException {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(KafkaProperties.TOPIC, new Integer(1));

        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());

        Map<String, List<KafkaStream<String, String>>> consumerMap = this.consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get(KafkaProperties.TOPIC).get(0);

        com.beifeng.handler.DTUMsgHandler handler = new com.beifeng.handler.DTUMsgHandler(stream,this.latch);

        handler.start();

        this.latch.await();
//        ConsumerIterator<String, String> it = stream.iterator();
//        while (it.hasNext())
//            System.out.println(it.next().message());
    }
}
