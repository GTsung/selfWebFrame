package com.self.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * @author GTsung
 * @date 2021/10/9
 */
public class DistributeAtomicInt {
    private static final String CONNECT = "192.168.197.130:2182";
    static  String distribute_atomic_path = "/atomic";
    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(CONNECT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args) throws Exception {
        client.start();
        DistributedAtomicInteger atomicInt = new DistributedAtomicInteger(client,
                distribute_atomic_path, new RetryNTimes(3, 1000));
        AtomicValue<Integer> rc = atomicInt.add(8);
        System.out.println(rc.succeeded());
    }
}
