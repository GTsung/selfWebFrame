package com.self.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class CreateSession {
    private static final String CONNECT = "192.168.197.130:2181,192.168.197.130:2182,192.168.197.130:2183";

    public static void main(String[] args) throws Exception{
        // 重试
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECT,
                5000, 3000, retryPolicy);
        client.start();
        Thread.sleep(Integer.MAX_VALUE);
        String path = "/zk-test";
        // 默认节点为持久，value为空
        client.create().forPath(path);
        client.create().forPath(path, "value".getBytes());
        client.create().withMode(CreateMode.EPHEMERAL).forPath(path);
        // 递归创建
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        // 删除节点
        client.delete().forPath(path);
        client.delete().withVersion(1).forPath(path);
        // 递归删除
        client.delete().deletingChildrenIfNeeded().forPath(path);
        // 强制保证删除
        client.delete().guaranteed().forPath(path);
        // 读取节点
        client.getData().forPath(path);
        // 读取节点并将状态放入到stat
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath(path);
        // 更新节点
        client.setData().forPath(path);
        // 更新版本
        client.setData().withVersion(1).forPath(path);
        client.setData().forPath(path, "ss".getBytes());
    }
}
