package com.self.zk;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkCreateAsync {

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

        // create path result :[rc=0,path=/zk-test-02,ctx=I'm a context,real path name=/zk-test-02
        zooKeeper.create("/zk-test-02", "kkk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallback(), "I'm a context");
        Thread.sleep(10000);
        // create path result :[rc=-110,path=/zk-test-02,ctx=I'm a context,real path name=null
        zooKeeper.create("/zk-test-02", "kkk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL, new IStringCallback(), "I'm a context");
        Thread.sleep(10000);
        // create path result :[rc=0,path=/zk-test-02,ctx=I'm a context,real path name=/zk-test-020000000005
        zooKeeper.create("/zk-test-02", "kkk".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL, new IStringCallback(), "I'm a context");
        Thread.sleep(Integer.MAX_VALUE);
    }
}

class IStringCallback implements AsyncCallback.StringCallback {
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("create path result :[rc=" + rc + ",path="
                + path + ",ctx=" + ctx + ",real path name=" + name);
    }
}
