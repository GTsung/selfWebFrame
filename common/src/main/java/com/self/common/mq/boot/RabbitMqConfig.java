package com.self.common.mq.boot;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GTsung
 * @date 2021/8/24
 */
@Configuration
public class RabbitMqConfig {

    private static final String TOPIC_ROUTING_KEY = "topic.*";

    @Bean
    public TopicExchange topicDemoExchange() {
        return new TopicExchange("topic_demo_exchange", true, false);
    }

    @Bean
    public Queue topicDemoQueue() {
        return new Queue("topic_demo_queue", true, false, false);
    }

    @Bean
    public Binding topicBind() {
        return BindingBuilder
                .bind(topicDemoQueue())
                .to(topicDemoExchange())
                .with(TOPIC_ROUTING_KEY);
    }
}
