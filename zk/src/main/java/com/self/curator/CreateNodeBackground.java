package com.self.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class CreateNodeBackground {
    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";
    static String path = "/zk-book";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(CONNECT)
            .sessionTimeoutMs(5000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();
    static CountDownLatch countDownLatch = new CountDownLatch(2);
    static ExecutorService tp = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws Exception {
        client.start();
        System.out.println("main thread:" + Thread.currentThread().getName());
        // 异步事件处理逻辑交由线程池做
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("event[code:" + event.getResultCode() + ",type:" +event.getType()+"]");
                System.out.println(Thread.currentThread().getName());
                countDownLatch.countDown();
            }
        }, tp).forPath(path, "init".getBytes());
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                        System.out.println("event[code:" + event.getResultCode() + ",type:" +event.getType()+"]");
                        System.out.println(Thread.currentThread().getName());
                        countDownLatch.countDown();
                    }
                }).forPath(path, "init".getBytes());
        countDownLatch.await();
        tp.shutdown();
    }
}
