package com.self.backend.controller;

import com.self.common.anno.OperationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * @author GTsung
 * @date 2021/8/19
 */
@Slf4j
@RestController
@RequestMapping("/redis/lock")
public class TestRedisLockController {

    private int count;

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @OperationResult
    @PutMapping
    public Object testLock() throws InterruptedException {
        Lock lock = redisLockRegistry.obtain("key");
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        count = 0;
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    int tempCount = count;
                    tempCount += 1;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        log.info("error {}", e.getMessage(), e);
                    }
                    count = tempCount;
                } finally {
                    countDownLatch.countDown();
                    lock.unlock();
                }
            }).start();
        }
        countDownLatch.await();
        System.out.println("final count:" + count);
        return count;
    }

}
