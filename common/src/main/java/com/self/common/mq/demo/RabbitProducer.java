package com.self.common.mq.demo;

import com.google.common.collect.ImmutableMap;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author GTsung
 * @date 2021/8/22
 */
public class RabbitProducer {

    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingKey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 5672;

    public static void main(String[] args) throws Exception {
//        testNormal();

//        testMandatory();

        testAlternateExchange();
    }

    /**
     * 备份交换机
     * @throws IOException
     * @throws TimeoutException
     */
    private static void testAlternateExchange() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        // 加入备份交换机的参数，其中alternate-exchange为备份交换机的参数，myAe为备份交换机的名称
        Map<String, Object> args = ImmutableMap.<String, Object>builder()
                .put("alternate-exchange", "myAe")
                .build();
        // 正常交换机
        channel.exchangeDeclare("normal_exchange", BuiltinExchangeType.DIRECT,
                true, false, args);
        // 备份交换机，类型为FANOUT
        channel.exchangeDeclare("myAe", BuiltinExchangeType.FANOUT, true, false, null);

        // 正常队列绑定
        channel.queueDeclare("normal_queue", true, false, false, null);
        channel.queueBind("normal_queue", "normal_exchange", "normalKey");

        // 路由不到的队列，用于保存路由不到的消息
        channel.queueDeclare("unRouting_queue", true, false, false, null);
        channel.queueBind("unRouting_queue", "myAe", "");

        // 发送给normal_exchange, routingKey如果是normalKey, 则放入到normal_queue队列
        // 发送给normal_exchange, routingKey如果不是normalKey, 则会路由到myAe交换机，接着放入到unRouting_queue队列

    }


    /**
     * mandatory参数，当交换机找不到队列时返回给生产者
     * @throws IOException
     * @throws TimeoutException
     */
    private static void testMandatory() throws IOException, TimeoutException {
        Connection connection = getConnection();
        Channel channel = connection.createChannel();
        // 声明了exchange，但没有声明queue
        channel.exchangeDeclare("exchange_mandatory", BuiltinExchangeType.FANOUT, true);
        // 向exchange发送消息，
        // mandatory属性设置为true，这个属性用于当交换器找不到对于的队列时返回给生产者，为false则直接将消息丢弃
        channel.basicPublish("exchange_mandatory", "", true,
                MessageProperties.PERSISTENT_TEXT_PLAIN, "mandatory_test".getBytes());

        // 生产者接受发送不到的消息处理
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("返回的结果是:" + msg);
            }
        });
    }

    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("root");
        factory.setPassword("123456");
        // 创建连接
        return factory.newConnection();
    }

    /**
     * 正常
     * @throws IOException
     * @throws TimeoutException
     */
    private static void testNormal() throws IOException, TimeoutException {
        Connection connection = getConnection();
        // 创建channel
        Channel channel = connection.createChannel();
        // type为交换机的类型，durable参数是持久化，autoDelete是否自动删除
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true, false, null);
        // 持久化的非排他的非自动删除的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "hello world";
        // 发送消息给交换机
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.close();
        connection.close();
    }

}
