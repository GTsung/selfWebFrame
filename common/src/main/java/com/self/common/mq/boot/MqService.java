package com.self.common.mq.boot;

public interface MqService {

    void send(String exchange, String routingKey, String value);

    void send(String routingKey, String value);
}
