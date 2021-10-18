package com.self.zk;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class ZkDeleteAsync {
    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECT, 6000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
            }
        });
        countDownLatch.await();
        String path1 = zooKeeper.create("/zk-test-01", "".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("创建节点：" + path1);
        Thread.sleep(20000);
        zooKeeper.delete("/zk-test-01", 0, new DeleteCallback(), "delete data");
        Thread.sleep(Integer.MAX_VALUE);
    }
}

class DeleteCallback implements AsyncCallback.VoidCallback {
    @Override
    public void processResult(int rc, String path, Object ctx) {
        System.out.println("删除结果：rc=" + rc +
                ",删除节点path=" + path + ",全局信息ctx=" + ctx);
    }
}