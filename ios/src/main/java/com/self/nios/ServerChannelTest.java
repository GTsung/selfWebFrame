package com.self.nios;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author GTsung
 * @date 2021/10/14
 */
public class ServerChannelTest {

    private static final String FF = "I must going.";

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 1234;
        ByteBuffer buffer = ByteBuffer.wrap(FF.getBytes());
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        // 非阻塞方式
        serverSocketChannel.configureBlocking(false);
        while (true) {
            System.out.println("waiting for connection");
            // 传统的ServerSocket会一直在这里阻塞等待
            SocketChannel sc = serverSocketChannel.accept();
            if (null == sc) {
                Thread.sleep(1000);
            } else {
                System.out.println("Incoming connection from " + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
