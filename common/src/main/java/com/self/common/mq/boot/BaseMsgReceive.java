package com.self.common.mq.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author GTsung
 * @date 2021/8/24
 */
@Slf4j
public abstract class BaseMsgReceive<T> {

    private static int RETRY_TIME = 5;

    private Map<T, Integer> countMap = new ConcurrentHashMap<>();

    /**
     * 接受消息，调用该方法可以实现重试
     * 需要加注解{@link org.springframework.amqp.rabbit.annotation.RabbitHandler}
     *
     * @param message
     */
    public void receive(T message) {
        log.debug("收到消息： {}", message);
        try {
            dealMessage(message);
            countMap.remove(message);
        } catch (Exception e) {
            log.warn("消息消费失败！");
            if (StringUtils.isEmpty(message)) {
                return;
            }
            Integer count = countMap.get(message) == null ? 0 : countMap.get(message);
            if (count >= RETRY_TIME) {
                // 到达重试次数最大值时，把消息保存到表中
                log.info("消息最终处理失败，保存消息->消息：{}", message);
                countMap.remove(message);
                return;
            }
            log.info("将会第{}次重试消息： {}", count, message);
            count++;
            countMap.put(message, count);
            throw e;
        }
    }

    /**
     * 处理消息方法
     *
     * @param message
     */
    public abstract void dealMessage(T message);

}
