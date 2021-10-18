package com.self.common.mq.high;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author GTsung
 * @date 2021/9/24
 */
@Slf4j
@Component
public class ConfirmPublish {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MyCallBack callBack;

    @PostConstruct
    public void init() {
        // 设置回调
        rabbitTemplate.setConfirmCallback(callBack);
        // mandatory为true时交换机收到消息但消息找不到队列，则将消息返回给生产者
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(callBack);
    }

    public void send(String msg) {
        rabbitTemplate.convertAndSend(ConfirmHighConfig.EXCHANGE,
                ConfirmHighConfig.ROUTING_KEY, msg, new CorrelationData("1"));
    }
}
