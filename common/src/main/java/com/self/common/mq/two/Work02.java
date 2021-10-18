package com.self.common.mq.two;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.self.common.mq.util.ConnectionUtil;

import java.io.IOException;

/**
 * @author GTsung
 * @date 2021/9/23
 */
public class Work02 {
    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        System.out.println("C2等待接收消息");
        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
            String msg = new String(delivery.getBody());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) { }
            System.out.println("C2接收到消息：" + msg);
            // 手动确认
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        });
        // 消费者手动确认
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, (consumerTag -> {
            System.out.println("C2取消消费接口回调");
        }));
    }
}
