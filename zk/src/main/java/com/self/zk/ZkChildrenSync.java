package com.self.zk;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkChildrenSync {

    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(CONNECT, 6000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    if (Event.EventType.None == event.getType() && null == event.getPath()) {
                        countDownLatch.countDown();
                    } else if(event.getType() == Event.EventType.NodeChildrenChanged){
                        try {
                            List<String> newChildren = zooKeeper.getChildren("/zk-test-01", true);
                            for (String s : newChildren) {
                                System.out.println(s);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });
        countDownLatch.await();
        zooKeeper.create("/zk-test-01", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create("/zk-test-01/c1", "abc".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        List<String> childrenList = zooKeeper.getChildren("/zk-test-01", true);
        for (String s : childrenList) {
            System.out.println(s);
        }
        zooKeeper.create("/zk-test-01/c2", "ss".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(10000);
        zooKeeper.create("/zk-test-01/c3", "ss".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
