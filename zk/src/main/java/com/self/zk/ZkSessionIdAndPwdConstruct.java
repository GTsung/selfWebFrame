package com.self.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkSessionIdAndPwdConstruct implements Watcher {

    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT, 5000,
                new ZkSessionIdAndPwdConstruct());
        countDownLatch.await();
        // 获取第一次zookeeper会话的id及密码，用于复用该会话
        long sessionId = zooKeeper.getSessionId();
        byte[] pwd = zooKeeper.getSessionPasswd();
        zooKeeper = new ZooKeeper(CONNECT, 5000, new ZkSessionIdAndPwdConstruct(),
                sessionId, pwd);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("receive watched event:" + event);
        if (Event.KeeperState.SyncConnected == event.getState()) {
            countDownLatch.countDown();
        }
    }
}
