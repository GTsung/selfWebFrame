package com.self.common.mq.four;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.self.common.mq.util.ConnectionUtil;

import java.io.IOException;

/**
 * @author GTsung
 * @date 2021/9/24
 */
public class DeadPro {
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        // 死信消息，设置ttl
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String msg = "info_" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "normal", properties, msg.getBytes());
        }
    }
}
