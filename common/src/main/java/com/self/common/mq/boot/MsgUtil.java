package com.self.common.mq.boot;

import com.self.common.utils.ObjectUtil;
import com.self.common.utils.SpringFactoryUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author GTsung
 * @date 2021/8/24
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MsgUtil {

    private static final String LOCK = "LOCK";
    private static final String MQ_SERVICE = "MqService";

    private static MqService mqService;

    private static MqService getService() {
        if (null == mqService) {
            synchronized (LOCK) {
                try {
                    mqService = (MqService) SpringFactoryUtil.getBean(MQ_SERVICE);
                } catch (Exception e) {
                    log.info("从上下文中获取失败，尝试构建，{}", e.getMessage(), e);
                    mqService = new MqServiceImpl();
                    try {
                        Field field = ObjectUtil.getField("rabbitTemplate", mqService.getClass());
                        field.setAccessible(true);
                        field.set(mqService, SpringFactoryUtil.getBean(RabbitTemplate.class));
                    } catch (Exception e1) {
                        log.error("获取字段rabbitTemplate失败", e1);
                    }
                    if (null != SpringFactoryUtil.getApplicationContext()) {
                        // 如果已经把上下文放入SpringFactoryUtil中，注册bean
                        SpringFactoryUtil.addBean(mqService.getClass(),
                                MQ_SERVICE,
                                new HashMap(0));
                    }
                }
            }
        }
        return mqService;
    }

    public static void send2Mq(String exchange, String routingKey, String value) {
        try {
            getService().send(exchange, routingKey, value);
        } catch (Exception e) {
            log.error("发送消息{}失败：{}", value, e);
        }
    }

}
