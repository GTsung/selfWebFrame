package com.self.common.mq.two;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.self.common.mq.util.ConnectionUtil;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author GTsung
 * @date 2021/9/23
 */
public class Task {

    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Scanner sc = new Scanner(System.in);
        System.out.println("输入消息");
        while(sc.hasNext()) {
            String msg = sc.nextLine();
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            System.out.println("生产者发送消息:" + msg);
        }
    }
}
