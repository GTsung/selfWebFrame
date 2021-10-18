package com.self.common.mq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.self.common.mq.util.ConnectionUtil;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 发布确认
 * 1.单个确认
 * 2.批量确认
 * 3.异步批量确认
 *
 * @author GTsung
 * @date 2021/9/23
 */
public class ConfirmMsg {

    public static int MSG_COUNT = 1000;

    public static void main(String[] args) {

    }

    /**
     * 单个确认
     */
    public static void publishMsgIndividual() throws IOException, InterruptedException {
        Channel channel = ConnectionUtil.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        // 发布确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();
        // 发消息
        for (int i = 0; i < MSG_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            // 单个消息就马上进行发布确认
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("发送成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MSG_COUNT + "个单独消息耗时：" + (end - begin));
    }

    /**
     * 批量确认，性能比单个确认高些
     */
    public static void publishMsgBatch() throws Exception {
        Channel channel = ConnectionUtil.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        // 发布确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();

        // 批量确认消息大小
        int batchSize = 100;
        for (int i = 0; i < MSG_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            // 批量消息确认
            if (i % batchSize == 0) {
                channel.waitForConfirms();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MSG_COUNT + "个批量消息耗时：" + (end - begin));
    }

    public static void publishAsync() throws Exception {
        Channel channel = ConnectionUtil.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        // 发布确认
        channel.confirmSelect();

        long begin = System.currentTimeMillis();

        ConcurrentSkipListMap<Long, String> map = new ConcurrentSkipListMap<>();

        // 准备消息监听器
        // 消息确认成功回调
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            System.out.println("确认消息" + deliveryTag);
            if (multiple) {
                // 批量处理
                ConcurrentNavigableMap<Long, String> confirmed = map.headMap(deliveryTag);
                confirmed.clear();
            } else {
                map.remove(deliveryTag);
            }
        };
        // 消息确认失败，回调
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("未确认的消息" + deliveryTag);
        };
        // 异步回调
        channel.addConfirmListener(ackCallback, nackCallback);
        for (int i = 0; i < MSG_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            map.put(channel.getNextPublishSeqNo(), message);
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MSG_COUNT + "个异步确认消息耗时：" + (end - begin));
    }
}
