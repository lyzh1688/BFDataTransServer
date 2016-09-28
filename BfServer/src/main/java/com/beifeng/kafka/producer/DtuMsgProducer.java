package com.beifeng.kafka.producer;

//import kafka.producer.Producer;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import java.util.Properties;
//import kafka.javaapi.producer.Producer;
/**
 * Created by 胡志洁 on 2016/6/15.
 */
public class DtuMsgProducer {

    private Properties props;

    private ProducerConfig config;

    private Producer<String, String> producer;

    public DtuMsgProducer() {
        this.props = new Properties();
        this.props.put("serializer.class", "kafka.serializer.StringEncoder");
        this.props.put("metadata.broker.list", Configure.KAFKA_IP + ":" + Configure.KAFKA_PORT);
        this.props.put("partitioner.class", "com.beifeng.kafka.Partitioner.DTUPartitioner");
        this.props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(this.props);
        this.producer = new Producer<String, String>(config);

    }

    public void produce(String key,String msg){
        this.producer.send(new KeyedMessage<String, String>(Configure.KAFKA_TOPIC ,msg));
        System.out.print(key + ":" + msg);
    }
}
