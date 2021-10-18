package com.self.common.mq.ttl;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author GTsung
 * @date 2021/9/24
 */
@Slf4j
@Component
public class DeadQueueConsumer {

    @RabbitListener(queues = "D")
    public void receiveD(Message msg, Channel channel) {
        String m = new String(msg.getBody());
        log.info("收到死信队列消息" + m);
    }
}
