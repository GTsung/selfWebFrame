package com.self.common.mq.boot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author GTsung
 * @date 2021/8/24
 */
@Service
public class MqServiceImpl implements MqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String exchange, String routingKey, String value) {
        rabbitTemplate.convertAndSend(exchange, routingKey, value);
    }

    @Override
    public void send(String routingKey, String value) {
        rabbitTemplate.convertAndSend(routingKey, value);
    }
}
