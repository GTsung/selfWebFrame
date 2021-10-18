package com.self.zk;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * 同步创建节点
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkCreateSync {

    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        // 分别是节点的路径、value值、权限、节点属性(节点是否有序、持久)
        String path1 = zooKeeper.create("/zk-test-01", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // success Node created:/zk-test-01
        System.out.println("success Node created:" + path1);
        // 临时顺序节点，当路径一致，且节点的属性也一致时，会报错
        // path2的属性为有序临时节点，因此会在节点后加上一个序列
        String path2 = zooKeeper.create("/zk-test-01", "abc".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        // path2 created:/zk-test-010000000002
        System.out.println("path2 created:" + path2);

    }
}
