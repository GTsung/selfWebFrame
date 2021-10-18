package com.self.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/10
 */
public class ZkLockSample {
    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";
    private ZooKeeper zooKeeper;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private CountDownLatch waitLatch = new CountDownLatch(1);
    String waitPath;
    String current;

    public ZkLockSample() throws Exception {
        // 获取连接
        zooKeeper = new ZooKeeper(CONNECT, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (Event.EventType.None == event.getType()) {
                    countDownLatch.countDown();
                }
                // 判断
                if (Event.EventType.NodeDeleted == event.getType() && event.getPath().equals(waitPath)) {
                    waitLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        // 判断是否存在
        Stat stat = zooKeeper.exists("/lock", false);
        if (stat == null) {
            zooKeeper.create("/lock", "lock".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public void zkLock() {
        // 创建临时有序节点
        try {
            current = zooKeeper.create("/lock/seq-", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            Thread.sleep(10);
            List<String> children = zooKeeper.getChildren("/lock", false);
            if (children.size() == 1) {
                return;
            } else {
                Collections.sort(children);
                String nodeName = current.substring("/lock/".length());
                int index = children.indexOf(nodeName);
                if (index == -1) {
                    System.out.println("error");
                } else if (index == 0) {
                    return;
                } else {
                    //  // 判断是否为最小节点,监听前一个节点
                    waitPath = "/lock/" + children.get(index - 1);
                    zooKeeper.getData(waitPath, true, null);
                    waitLatch.await();
                }
            }
        } catch (Exception e) {
        }

    }

    public void zkUnLock() {
        try {
            zooKeeper.delete(current, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ZkLockSample lock1 = new ZkLockSample();
        ZkLockSample lock2 = new ZkLockSample();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.zkLock();
                    System.out.println("线程1启动");
                    Thread.sleep(10000);
                    lock1.zkUnLock();
                    System.out.println("线程1结束");
                } catch (Exception e) {

                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.zkLock();
                    System.out.println("线程2启动");
                    Thread.sleep(10000);
                    lock2.zkUnLock();
                    System.out.println("线程2结束");
                } catch (Exception e) {

                }
            }
        }).start();
    }
}
