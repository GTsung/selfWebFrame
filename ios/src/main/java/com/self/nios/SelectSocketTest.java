package com.self.nios;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author GTsung
 * @date 2021/10/14
 */
public class SelectSocketTest {

    public static int PORT =1234;

    

    public static void main(String[] args) throws IOException {
        new SelectSocketTest().go();

    }

    private void go() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket ssk = serverSocketChannel.socket();
        Selector selector = Selector.open();
        ssk.bind(new InetSocketAddress(PORT));
        serverSocketChannel.configureBlocking(false);
        // 注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            int n = selector.select();
            if (n == 0) {
                continue;
            }
            Iterator it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel sc = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = sc.accept();
                    regiesterChannel(selector, socketChannel, SelectionKey.OP_READ);
                    sayHello(socketChannel);
                }
                if (key.isReadable()) {
                    readDataFromSocket(key);
                }
                it.remove();
            }
        }
    }



    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

    private void readDataFromSocket(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        byteBuffer.clear();
        // 继续将channel里的内容写入到缓冲区
        while ((count = socketChannel.read(byteBuffer)) >0) {
            byteBuffer.flip();
            // 继续读到通道中
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
            byteBuffer.clear();
        }
        if (count < 0) {
            socketChannel.close();
        }
    }

    private void sayHello(SocketChannel socketChannel) throws IOException {
        byteBuffer.clear();
        byteBuffer.put("Hi there!\r\n".getBytes());
        byteBuffer.flip();
        // 将缓冲区的内容读入到socketChannel中
        socketChannel.write(byteBuffer);
    }

    private void regiesterChannel(Selector selector, SocketChannel socketChannel, int key) throws IOException {
        if (socketChannel == null) return;
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, key);
    }
}
