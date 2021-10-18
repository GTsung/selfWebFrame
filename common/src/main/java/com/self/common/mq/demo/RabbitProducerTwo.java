package com.self.common.mq.demo;

import com.google.common.collect.ImmutableMap;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author GTsung
 * @date 2021/8/22
 */
public class RabbitProducerTwo {


    public static void main(String[] args) throws IOException, TimeoutException {
//        testTtl();
//        testTtlOnMsg();
        testDead();
        testPriority();
    }

    // 优先级
    private static void testPriority() throws IOException, TimeoutException {
        Connection connection = RabbitProducer.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange_priority", "direct", true);
        // 设置队列优先级
        Map<String, Object> args = ImmutableMap.<String, Object>builder()
                .put("x-max-priority", 10)
                .build();
        channel.queueDeclare("queue_priority", true, false, false, args);
        // 设置消息的优先级
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.priority(5);
        AMQP.BasicProperties properties = builder.build();
        channel.basicPublish("exchange_priority", "rk_priority",
                properties, "message".getBytes());
    }

    // 消息中设置TTL
    private static void testTtlOnMsg() throws IOException, TimeoutException {

        Connection connection = RabbitProducer.getConnection();
        Channel channel = connection.createChannel();
        // 声明交换机、队列
        channel.exchangeDeclare("exchange_name", "direct", true);
        channel.queueDeclare("queue_name", true, false, false, null);
        channel.queueBind("queue_name", "exchange_name", "routingKey");

        // 设置消息属性
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2); // 持久化消息
        builder.expiration("60000"); // 设置TTL
        AMQP.BasicProperties properties = builder.build();
        channel.basicPublish("exchange_name", "routingKey",
                false, properties, "ttlMsg".getBytes());
    }

    // 队列设置TTL
    private static void testTtl() throws IOException, TimeoutException {
        Connection connection = RabbitProducer.getConnection();
        Channel channel = connection.createChannel();
        // 设置队列的TTL，这个队列中所有的消息过期时间为60s，
        // 如果这个参数设置成0，则除非消费者可以立即消费，不然会直接删除
        // 不设置则消息不会过期
        Map<String, Object> args = ImmutableMap.<String, Object>builder()
                .put("x-message-ttl", 6000)
                .build();
        channel.queueDeclare("ttl_queue", true,
                false, false, args);
    }

    // 死信队列的配置
    private static void testDead() throws IOException, TimeoutException {
        Connection connection = RabbitProducer.getConnection();
        Channel channel = connection.createChannel();
        // 声明死信交换机、正常交换机
        // 死信交换机
        channel.exchangeDeclare("exchange.dlx", "direct", true);
        channel.exchangeDeclare("exchange.normal", "fanout", true);
        // 正常队列的参数配置
        Map<String, Object> args = ImmutableMap.<String, Object>builder()
                // 配置过期时间 TTL
                .put("x-message-ttl", 10000)
                // 设置死信交换机 DLX
                .put("x-dead-letter-exchange", "exchange.dlx")
                // 设置死信交换的路由键 DLK
                .put("x-dead-letter-routing-key", "routingKey")
                .build();
        // 正常队列
        channel.queueDeclare("queue.normal", true, false, false, args);
        channel.queueBind("queue.normal", "exchange.normal", "");
        // 死信队列
        channel.queueDeclare("queue.dlx", true, false, false, null);
        // 死信路由
        channel.queueBind("queue.dlx", "exchange.dlx", "routingKey");
        // 生产者通过路由键rk发送到正常交换机放到正常队列中，正常队列设置过期时间，时间过期后会将消息放到死信交换机
        // 死信交换机将消息放到死信队列中，后续消费者可以通过routingKey路由到死信队列对消息进行分析
        channel.basicPublish("exchange.normal", "rk",
                MessageProperties.PERSISTENT_TEXT_PLAIN, "dlx".getBytes());
    }


}
