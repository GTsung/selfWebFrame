package com.self.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkSetDataSync {

    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;

    public static void main(String[] args) throws Exception {
        String path = "/zk-book";
        zooKeeper = new ZooKeeper(CONNECT, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    if (Event.EventType.None == event.getType() && null == event.getPath()) {
                        countDownLatch.countDown();
                    }
                }
            }
        });
        countDownLatch.await();
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData(path, true, null);
        Stat stat = zooKeeper.setData(path, "456".getBytes(), -1);
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
        Stat stat2 = zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
        System.out.println(stat2.getCzxid() + "," + stat2.getMzxid() + "," + stat2.getVersion());
        try {
            zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(Integer.MAX_VALUE);
    }
}
