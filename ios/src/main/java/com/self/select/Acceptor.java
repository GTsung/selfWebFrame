package com.self.select;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author GTsung
 * @date 2021/11/21
 */
public class Acceptor implements Runnable {

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    private final ServerSocketChannel serverSocketChannel;

    public Acceptor(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel channel = serverSocketChannel.accept();
            if (null != channel) {
                executorService.execute(new Handler(channel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
