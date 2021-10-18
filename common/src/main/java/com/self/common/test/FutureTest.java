package com.self.common.test;

import java.util.concurrent.CompletableFuture;

/**
 * @author GTsung
 * @date 2021/10/1
 */
public class FutureTest {

    public static void main(String[] args) {
        CompletableFuture f1 = CompletableFuture.runAsync(() -> {
            System.out.println("洗水浒");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("烧开水");
        });

        CompletableFuture f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("洗茶壶");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("拿茶叶");
            return "普洱茶";
        });

        CompletableFuture f3 = f1.thenCombine(f2, (__,tf)->{
            System.out.println("拿到茶叶"+ tf);
            return "上茶";
        });

        f3.join();

    }
}
