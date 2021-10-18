package com.self.common.delay.service;

import com.self.common.delay.domain.DelayedElement;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author GTsung
 * @date 2021/8/20
 */
@Slf4j
public abstract class BaseDelayQueueHandler {

    private static final DelayQueue<DelayedElement> QUEUE = new DelayQueue<>();

    private static final long EXPIRE_TIME = 30 * 1000L;

    private static final Map<Integer, BaseDelayQueueHandler> HANDLER_MAP = new ConcurrentHashMap<>();

    protected final void register(Integer handlerKey, BaseDelayQueueHandler queueHandler) {
        HANDLER_MAP.put(handlerKey, queueHandler);
    }

    protected BaseDelayQueueHandler() {
        DelayQueueTask delayQueueTask = new DelayQueueTask();
        ExecutorService executorService = Executors.newSingleThreadExecutor(new DelayQueueThreadFactory(delayQueueTask));
        executorService.submit(delayQueueTask);
    }

    public static void submit(Integer handlerKey, Object value) {
        log.info("handlerKey:{}，value:{}，放入延迟队列", handlerKey, value);
        DelayedElement element = new DelayedElement(handlerKey, value, EXPIRE_TIME);
        QUEUE.offer(element);
    }

    public static class DelayQueueThreadFactory implements ThreadFactory {

        private DelayQueueTask task;

        public DelayQueueThreadFactory(DelayQueueTask task) {
            super();
            this.task = task;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler((t, e) -> {
                ExecutorService pool = Executors.newSingleThreadExecutor(new DelayQueueThreadFactory(task));
                pool.execute(task);
                pool.shutdown();
                log.error("原有线程（{}）意外死亡，获取新的线程", t.getName());
            });
            return thread;
        }
    }

    public static class DelayQueueTask implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    DelayedElement element = QUEUE.take();
                    Integer handlerKey = element.getHandlerKey();
                    Object value = element.getValue();
                    // 策略模式处理业务
                } catch (Exception e) {
                    log.info("获取延迟队列元素失败，{}", e.getMessage(), e);
                }
            }
        }
    }
}
