package com.self.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkSimpleConstruct {

    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {

        // 第一个参数为连接地址，第二个参数为session过期时间，默认毫秒，第三个参数为watcher监听
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("receive watched event:" + event);
                if (Event.KeeperState.SyncConnected == event.getState()) {
                    countDownLatch.countDown();
                }
            }
        });
        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        System.out.println("zookeeper session established");

    }
}
