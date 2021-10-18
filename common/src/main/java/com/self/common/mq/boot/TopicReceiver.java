package com.self.common.mq.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author GTsung
 * @date 2021/8/24
 */
@Slf4j
@Component
@RabbitListener(queues = "topic_demo_queue")
public class TopicReceiver  extends BaseMsgReceive<String>{


    @Override
    @RabbitHandler
    public void receive(String message) {
        super.receive(message);
    }

    @Override
    public void dealMessage(String message) {
        System.out.println(message);
    }
}
