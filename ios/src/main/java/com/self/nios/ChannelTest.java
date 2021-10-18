package com.self.nios;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author GTsung
 * @date 2021/10/14
 */
public class ChannelTest {
    public static void main(String[] args) throws IOException {
        // Socket的通道
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 9001));
        // ServerSocket的通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9880));
        // DatagramChannel的通道
        DatagramChannel datagramChannel = DatagramChannel.open();
        // File的通道只能通过RandomAccessFile、FileInputStream、FileOutputStream来创建
        RandomAccessFile accessFile = new RandomAccessFile(new File("ss."),"r");
        FileChannel fileChannel = accessFile.getChannel();
    }
}
