package com.self.common.delay.service;

import com.self.common.delay.enums.DelayTaskTypeEnum;
import com.self.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author GTsung
 * @date 2021/8/20
 */
@Slf4j
public abstract class BaseDelayTaskHandler implements DelayTaskService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired(required = false)
    private RedisLockRegistry redisLockRegistry;

    // redis的zSet键
    private String zSetName;

    private static final Map<String, BaseDelayTaskHandler> HANDLER_MAP = new ConcurrentHashMap<>();

    /**
     * 策略类初始化后将策略注册
     *
     * @param typeKey
     * @param taskHandler
     */
    protected final void register(String typeKey, BaseDelayTaskHandler taskHandler) {
        HANDLER_MAP.put(typeKey, taskHandler);
        zSetName = typeKey;
    }

    /**
     * 策略类调用，开始进入延时队列
     *
     * @param taskTypeEnum
     * @param businessId
     */
    @Override
    public void start(DelayTaskTypeEnum taskTypeEnum, String businessId) {
        System.out.println(String.format("%s,开始这个任务的延时", taskTypeEnum.getType()));
        redisUtil.zsSet(zSetName, String.format("%s.%s", taskTypeEnum.getType(), businessId), System.currentTimeMillis() + taskTypeEnum.getExpireTime() * 1000);
    }

    @Override
    public abstract void afterExpire(String businessId);

    @Scheduled(cron = "0/1 * * * * ?")
    private void schedule() {
        Lock lock = redisLockRegistry.obtain(zSetName);
        try {
            // 获取分布式锁
            if (!lock.tryLock(1, TimeUnit.SECONDS)) {
                return;
            }
            process();
        } catch (InterruptedException e) {
            log.info("获取锁失败，{}", e.getMessage(), e);
        } catch (Exception e) {
            log.info("定时任务失败，{}", e.getMessage(), e);
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                log.info("解锁失败，{}", e.getMessage(), e);
            }
        }
    }

    private void process() {
        Set<Object> values = redisUtil.zsRangeByScore(zSetName, 0, System.currentTimeMillis());
        if (CollectionUtils.isEmpty(values)) {
            return;
        }
        for (Object value : values) {
            String[] params = value.toString().split("\\.");
            if (params.length != 2) {
                redisUtil.zsRemove(zSetName, value);
                continue;
            }
            String taskType = params[0];
            BaseDelayTaskHandler handler = HANDLER_MAP.get(taskType);
            if (Objects.isNull(handler)) {
                log.info("找不到对应的处理类{}", value.toString());
                redisUtil.zsRemove(zSetName, value);
                continue;
            }
            String businessId = params[1];
            try {
                handler.afterExpire(businessId);
                // 执行完毕删除
                redisUtil.zsRemove(zSetName, value);
            } catch (Exception e) {
                log.info("定时任务失败，{}", e.getMessage(), e);
            }
        }
    }
}
