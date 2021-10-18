package com.self.common.mq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.self.common.mq.util.ConnectionUtil;

import java.io.IOException;

/**
 * @author GTsung
 * @date 2021/9/24
 */
public class DeadConsumer2 {

    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("consumer1" + new String(message.getBody()));
        };
        // 消费死信队列
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, custumerTag->{});
    }
}
