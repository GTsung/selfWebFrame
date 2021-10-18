package com.self.common.mq.one;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author GTsung
 * @date 2021/9/23
 */
public class Producer {

    public static final String QUEUE_NAME  = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 声明一个队列
         * 第一个参数为队列名称
         * 第二个参数durable是否持久化到磁盘
         * 第三个参数是否为排他队列，排他队列针对一个连接，这个连接中的其他信道可以找到该队列，其他链接则不能声明此队列
         * 第四个参数为是否自动删除
         * 第五个参数为属性
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "hello";
        /**
         * 第一个参数为交换机
         * 第二个参数为队列名称
         */
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
    }
}
