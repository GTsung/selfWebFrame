package com.self.common.mq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author GTsung
 * @date 2021/9/23
 */
public class Consumer {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 接收消息
        DeliverCallback delivery = (String consumerTag, Delivery message) -> {
            System.out.println(message); // 直接打印
        };
        // 消息中断的回调
        CancelCallback cancel = (String consumerTag) -> {
            System.out.println("消息中断");
        };
        /**
         * 消费
         * 第一个参数为队列
         * 第二个参数autoAck为是否自动确认
         * 第三个参数DeliverCallback为消息接收成功后调用
         * 第四个参数CancelCallback为取消消息时的回调
         */
        channel.basicConsume(QUEUE_NAME, true, delivery, cancel);

    }
}
