package com.self.common.mq.high;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GTsung
 * @date 2021/9/24
 */
@Configuration
public class ConfirmHighConfig {

    public static final String EXCHANGE = "confirm_exchange";
    public static final String QUEUE = "confirm_queue";
    public static final String ROUTING_KEY = "key";

    @Bean("confirmExchange")
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean("confirmQueue")
    public Queue queue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding queueBind(@Qualifier("confirmExchange")DirectExchange exchange,
                             @Qualifier("confirmQueue") Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

}
