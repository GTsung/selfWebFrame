package com.self.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkDataSync {
    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        String path  ="/zk-book";
        zooKeeper = new ZooKeeper(CONNECT, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    if (Event.EventType.None == event.getType() && null == event.getPath()) {
                        countDownLatch.countDown();
                    } else if(event.getType() == Event.EventType.NodeDataChanged){
                        try {
                            System.out.println(new String(zooKeeper.getData(event.getPath(), true, stat)));
                            System.out.println(stat.getCzxid()+"," + stat.getMzxid() + "," + stat.getVersion());
                        } catch (Exception e) {
                        }
                    }
                }
            }
        });
        countDownLatch.await();
        zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(new String(zooKeeper.getData(path, true, stat)));
        System.out.println(stat.getCzxid()+"," + stat.getMzxid() + "," + stat.getVersion());
        zooKeeper.setData(path, "123".getBytes(), -1);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
