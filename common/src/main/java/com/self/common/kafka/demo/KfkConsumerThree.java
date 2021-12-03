package com.self.common.kafka.demo;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;

/**
 * @author GTsung
 * @date 2021/12/3
 */
public class KfkConsumerThree {
    private static final String TOPIC_NAME = "my_topic";

    // server.broker
    private static final String SERVERS = "192.168.197.130:9092,192.168.197.130:9093,192.168.197.130:9094";
    // group.id
    private static final String CONSUMER_GROUP = "group_01";

    /**
     * 用于跟踪偏移量的map
     */
    private static Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();

    public static void main(String[] args) {
        Properties kfkProp = new Properties();
        kfkProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
        kfkProp.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        kfkProp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kfkProp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kfkProp.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kfkProp);
        try {
            consumer.subscribe(Collections.singletonList(TOPIC_NAME), new ConsumerRebalanceListener() {
                @Override
                public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                    // 此方法会在再均衡(消费者分配新分区或移除新分区)开始之前和消费者停止读取消息之后调用，
                    // 在此时提交偏移量，则下一个接管分区的消费者就知道从哪里开始读取了
                    System.out.println("Lost partition in rebalance. Committing current offsets:" + currentOffsets);
                    // 发生再均衡时，再即将失去分区所有权时提交偏移量，此时提交的是所有分区的最近处理的偏移量
                    consumer.commitSync(currentOffsets);
                }

                @Override
                public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                    // 此方法会在重新分配分区之后和消费者开始读取消息之前调用
                }
            });

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("topic=" + record.topic() + ",partition=" + record.partition() +
                            ",offset=" + record.offset() + ",key=" + record.key() + ",value=" + record.value());
                    currentOffsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, "no metadata"));
                }
                consumer.commitAsync(currentOffsets, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                consumer.commitSync(currentOffsets);
            } finally {
                consumer.close();
                System.out.println("closed consumer");
            }
        }
    }
}
