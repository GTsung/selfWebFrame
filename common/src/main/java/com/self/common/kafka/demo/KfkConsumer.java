package com.self.common.kafka.demo;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @author GTsung
 * @date 2021/12/2
 */
public class KfkConsumer {

    // topic-name
    private static final String TOPIC_NAME = "my-topic";
    // server.broker
    private static final String SERVERS = "192.168.197.130:9092,192.168.197.130:9093,192.168.197.130:9094";
    // group.id
    private static final String CONSUMER_GROUP = "group_01";

    public static void main(String[] args) {
        Properties kfkProp = new Properties();
        kfkProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
        kfkProp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kfkProp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 消费者组名
        kfkProp.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP);
        // 自动提交offset及间隔时间
//        kfkProp.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
//        kfkProp.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        // 手动提交offset
        kfkProp.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        // 一次poll的最大长度
        kfkProp.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        // 超过时间消费则剔除消费者
        kfkProp.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);

        // 消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kfkProp);
        // 订阅topic,可以订阅多个topic,可以指定一个正则表达式例如test.*
        consumer.subscribe(Collections.singletonList(TOPIC_NAME));

//        // 指定分区消费
//        consumer.assign(Collections.singletonList(new TopicPartition(TOPIC_NAME, 0)));
//        // 从头消费
//        consumer.seekToBeginning(Collections.singletonList(new TopicPartition(TOPIC_NAME, 0)));
//        // 指定offset消费
//        consumer.seek(new TopicPartition(TOPIC_NAME, 0), 10);

        // 指定时间点消费
        // 拿到主题的所有分区
//        List<PartitionInfo> topicPartitions = consumer.partitionsFor(TOPIC_NAME);
//        long fetchTime = new Date().getTime() - 1000 * 60 * 60;
//        Map<TopicPartition, Long> map = new HashMap<>();
//        for (PartitionInfo par : topicPartitions) {
//            map.put(new TopicPartition(TOPIC_NAME, par.partition()), fetchTime);
//        }
//        // 获取时间段内的
//        Map<TopicPartition, OffsetAndTimestamp> parMap = consumer.offsetsForTimes(map);
//        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : parMap.entrySet()) {
//            TopicPartition key = entry.getKey();
//            // 拿到该分区的一小时前的偏移量
//            OffsetAndTimestamp value = entry.getValue();
//            if (key == null || value == null) continue;
//            Long offset = value.offset();
//            System.out.println("partition-" + key.partition() + ",offset-" + offset);
//            if (value != null) {
//                // 指定分区消费，指定offset消费
//                consumer.assign(Collections.singletonList(key));
//                consumer.seek(key, offset);
//            }
//        }


        // 轮询获取消息
        try {
            while (true) {
                // 轮询，poll()参数是等待消费者缓冲区可用数据的时间，在等待时间内会阻塞知道broker有数据
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(String.format("收到消息，partition = %d, offset = %d ,key = %s, value= %s",
                            record.partition(), record.offset(), record.key(), record.value()));
                }

                // 手动提交
                if (records.count() > 0) {
                    // 1.同步提交，需要broker返回ack,可以try catch
                    consumer.commitSync();
                    // 2.异步提交
                    consumer.commitAsync(new OffsetCommitCallback() {
                        @Override
                        public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                            if (null != exception) {
                                System.out.println("commit failed for " + offsets);
                                exception.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
