package com.self.nios;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author GTsung
 * @date 2021/10/14
 */
public class SocketChannelTest {

    public static void main(String[] args) throws IOException {
        String hostname = "localhost";
        int port = 1234;
        InetSocketAddress socketAddress = new InetSocketAddress(hostname, port);
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        System.out.println("connecting ...");
        sc.connect(socketAddress);
        while (!sc.finishConnect()) {
            System.out.println("干");
        }
        System.out.println("finish");
        sc.close();
    }
}
