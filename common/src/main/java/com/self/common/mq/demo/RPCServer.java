package com.self.common.mq.demo;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author GTsung
 * @date 2021/8/23
 */
public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("root");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        System.out.println("[x] Awaiting RPC requests");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties.Builder()
                        .correlationId(properties.getCorrelationId())
                        .build();
                String response = "";
                try {
                    String message = new String(body, StandardCharsets.UTF_8);
                    int n = Integer.parseInt(message);
                    System.out.println("[.] fib(" + message + ")");
                    response += fib(n);
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    channel.basicPublish("", properties.getReplyTo(),
                            replyProps, response.getBytes(StandardCharsets.UTF_8));
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
    }

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }
}
