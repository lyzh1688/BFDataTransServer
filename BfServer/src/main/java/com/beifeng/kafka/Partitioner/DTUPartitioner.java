package com.beifeng.kafka.Partitioner;

import com.beifeng.kafka.producer.Configure;
import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * Created by 胡志洁 on 2016/6/18.
 */
public class DTUPartitioner implements Partitioner {

    public DTUPartitioner(VerifiableProperties props) {


    }


    public int partition(Object key, int partitionCount) {
        return Integer.valueOf((String) key) % Configure.KAFKA_PART_NUM;
    }
}
