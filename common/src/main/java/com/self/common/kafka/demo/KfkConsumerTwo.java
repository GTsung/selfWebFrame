package com.self.common.kafka.demo;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author GTsung
 * @date 2021/12/3
 */
public class KfkConsumerTwo {

    private static final String TOPIC_NAME = "my_topic";

    // server.broker
    private static final String SERVERS = "192.168.197.130:9092,192.168.197.130:9093,192.168.197.130:9094";
    // group.id
    private static final String CONSUMER_GROUP = "group_01";

    /**
     * 用于跟踪偏移量的map
     */
    private static Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    private static int count = 0;

    public static void main(String[] args) {
        Properties kfkProp = new Properties();
        kfkProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
        kfkProp.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        kfkProp.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        kfkProp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kfkProp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kfkProp);
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("topic=" + record.topic() + ",partition=" + record.partition()
                        + ",offset=" + record.offset() + ",key=" + record.key() + ",value=" + record.value());
                // 读取每条记录后，使用期望处理的下一条消息的偏移量更新map的偏移量
                // 下一次从这里开始读取消息，每个主题的每个分区的偏移量更新
                currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
                        new OffsetAndMetadata(record.offset() + 1, "no metadata"));
                // 每消费1000条提交偏移量
                if (count % 1000 == 0) {
                    // 将偏移量进行提交，这种方式可以在消费中进行提交，不需要都消费完毕再提交
                    consumer.commitAsync(currentOffsets, null);
                }
                count++;
            }
        }
    }
}
