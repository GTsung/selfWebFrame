package com.self.common.kafka.demo;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author GTsung
 * @date 2021/12/2
 */
public class KfkProducer {

    // 生产者需要三个必选属性
    /**
     * 1.bootstrap.servers:broker地址，格式为host:port
     * 2.key.serializer:将键序列化，
     * 查用的ByteArraySerializer/StringSerializer/IntegerSerializer
     * 3.value.serializer:value的序列化器
     */

    // topic-name
    private static final String TOPIC_NAME = "my-topic";
    // server.broker
    private static final String SERVERS = "192.168.197.130:9092,192.168.197.130:9093,192.168.197.130:9094";

    public static void main(String[] args) {
        Properties kfkProp = new Properties();
        // 指定server.broker地址
        kfkProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SERVERS);
        // key.serializer
        kfkProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.serializer
        kfkProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // ack
        kfkProp.put(ProducerConfig.ACKS_CONFIG, 1);
        // retry次数
        kfkProp.put(ProducerConfig.RETRIES_CONFIG, 3);
        // retry时间
        kfkProp.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 300);
        // buffer.memory
        kfkProp.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33553342);
        // batch.size
        kfkProp.put(ProducerConfig.BATCH_SIZE_CONFIG, 16721);
        // linger.ms
        kfkProp.put(ProducerConfig.LINGER_MS_CONFIG, 10);


        KafkaProducer<String, String> producer = new KafkaProducer<>(kfkProp);
        // 消息记录,key确定发往哪个分区，分区也可以指定，value为消息内容
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME, "myKey", "hello");
        // 同步获取结果,send方法返回的是Future<RecordMetadata>
        RecordMetadata recordMetadata = null;
        try {
            recordMetadata = producer.send(producerRecord).get();
            System.out.println("同步发送消息topic:" + recordMetadata.topic() +
                    ";分区为:" + recordMetadata.partition() + "偏移量:" + recordMetadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
            // 同步发送异常后，发起重试，记录日志
            try {
                Thread.sleep(2000);
                recordMetadata = producer.send(producerRecord).get();
            } catch (Exception e1) {
                // 人工介入
            }
        }

        // 异步发送消息
        producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    System.out.println("发送消息发生异常," + exception.getStackTrace());
                }
                if (metadata != null) {
                    System.out.println("同步发送消息topic:" + metadata.topic() +
                            ";分区为:" + metadata.partition() + "偏移量:" + metadata.offset());
                }
            }
        });
    }
}
