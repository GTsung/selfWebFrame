package com.self.common.delay.domain;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author GTsung
 * @date 2021/8/20
 */
@Slf4j
public class DelayedElement implements Delayed {

    /**
     * 键
     */
    @Getter
    private Integer handlerKey;

    /**
     * 值
     */
    @Getter
    private Object value;

    /**
     * 到期时间
     */
    private Long expireTime;

    public DelayedElement(Integer handlerKey, Object value, long expire) {
        this.handlerKey = handlerKey;
        this.value = value;
        this.expireTime = expire + System.currentTimeMillis();
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }
}
