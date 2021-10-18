package com.self.common.mq.four;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.self.common.mq.util.ConnectionUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author GTsung
 * @date 2021/9/24
 */
public class DeadConsumer1 {

    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        // 正常交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 死信交换机
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 普通队列
        Map<String, Object> arguments = new HashMap<>();
        // 设置消息的ttl
//        arguments.put("x-message-ttl", 10000);
        // 死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 死信路由
        arguments.put("x-dead-letter-routing-key", "dead");
        // 设置正常队列的容量
        arguments.put("x-max-length", 6);
        // 正常队列接收消息如果过期会转到死信交换机
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        // 死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);


        // 绑定正常队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "normal");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "dead");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody());
            if ("info_2".equals(msg)) {
                // 拒绝走死信队列
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println("consumer1" + msg);
            }

        };

        channel.basicConsume(NORMAL_QUEUE, true, deliverCallback, custumerTag->{});
    }
}
