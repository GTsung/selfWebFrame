package com.self.common.mq.ttl;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GTsung
 * @date 2021/9/24
 */
@Configuration
public class TtlConfig {

    public static final String X_EXCHANGE = "X";
    public static final String Y_EXCHANGE = "Y";
    public static final String QUEUE_A = "A";
    public static final String QUEUE_B = "B";
    public static final String QUEUE_DEAD = "D";

    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    // 死信交换机
    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_EXCHANGE);
    }

    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> arguments = new HashMap<>();
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_EXCHANGE);
        // 死信路由
        arguments.put("x-dead-letter-routing-key", "DE");
        // 设置TTL
//        arguments.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }

    @Bean("queueB")
    public Queue queueB() {
        Map<String, Object> arguments = new HashMap<>();
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_EXCHANGE);
        // 死信路由
        arguments.put("x-dead-letter-routing-key", "DE");
        // 设置TTL
        arguments.put("x-message-ttl", 40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    // 死信队列
    @Bean("queueD")
    public Queue queueD() {
        return QueueBuilder.durable(QUEUE_DEAD).build();
    }

    @Bean
    public Binding queueAbindX(@Qualifier("queueA")Queue queueA,
                               @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    @Bean
    public Binding queueBbindX(@Qualifier("queueB")Queue queueB,
                               @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    @Bean
    public Binding queueDbindY(@Qualifier("queueD")Queue queueD,
                               @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String msg = "ss";
        rabbitTemplate.convertAndSend("X", "XA", msg, message->{
            // MessagePostProcessor设置ttl
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
    }

//    @Bean
//    public CustomExchange delayedExchange() {
//        Map<String, Object> arguments = new HashMap<>();
//        arguments.put("x-delayed-type", "direct");
//        return new CustomExchange("delayedExchange", "x-delayed-message",
//                true, false, arguments);
//    }

}
