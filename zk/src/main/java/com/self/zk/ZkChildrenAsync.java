package com.self.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkChildrenAsync {

    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zooKeeper = new ZooKeeper(CONNECT, 4000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    if (Event.EventType.None == event.getType() && null == event.getPath()) {
                        countDownLatch.countDown();
                    } else if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        try {
                            List<String> newChildren = zooKeeper.getChildren(event.getPath(), true);
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
        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getChildren(path, true, new IChildren2Callback(), null);
        zooKeeper.create(path + "/c2", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(Integer.MAX_VALUE);
    }
}

class IChildren2Callback implements AsyncCallback.Children2Callback {
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        System.out.println("get result : rc=" + rc + ",path=" +
                path + ",ctx=" + ctx + ",children=" + children + ",stat=" + stat);
    }
}