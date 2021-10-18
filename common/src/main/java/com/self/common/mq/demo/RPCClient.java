package com.self.common.mq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author GTsung
 * @date 2021/8/23
 */
public class RPCClient {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;
//    private QueueingConsumer consumer;

    public RPCClient() {

    }
}
