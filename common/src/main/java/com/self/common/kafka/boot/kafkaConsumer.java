package com.self.common.kafka.boot;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static com.self.common.kafka.boot.KfkConstant.CONSUMER_GROUP;
import static com.self.common.kafka.boot.KfkConstant.TOPIC_NAME;

/**
 * @author GTsung
 * @date 2021/12/3
 */
@Component
public class kafkaConsumer {

    @KafkaListener(groupId = CONSUMER_GROUP, topics = TOPIC_NAME)
    public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.println(record.key());
        System.out.println(record.value());
        // 手动提交
        ack.acknowledge();
    }

    @KafkaListener(groupId = CONSUMER_GROUP,
            // 指定topic与分区
            topicPartitions = {
                    @TopicPartition(topic = "one", partitions = {"0", "1"}),
                    // 0分区
                    @TopicPartition(topic = "one", partitions = "0",
                            // 1分区从偏移量100开始消费
                            partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))},
            // 消费者组总共几个消费者，配置个数小于等于分区个数
            concurrency = "3")
    public void listenGroup(ConsumerRecord<String, String> record, Acknowledgment ack) {
        System.out.println(record.key());
        System.out.println(record.value());
        // 手动提交
        ack.acknowledge();
    }
}
