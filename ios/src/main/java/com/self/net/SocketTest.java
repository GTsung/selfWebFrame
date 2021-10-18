package com.self.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * @author GTsung
 * @date 2021/10/12
 */
public class SocketTest {

    public static void main(String[] args) throws Exception {
        // 创建一个未连接的套接字
        Socket socket = new Socket();
        // 连接到指定服务器，并指定超时时间
        socket.connect(new InetSocketAddress("localhost",8189), 20000);
//        boolean flag = socket.isConnected();
//        socket.isClosed();

        // 直接创建
//        Socket socket1  = new Socket("www.baidu.com", 8080);
//        socket1.setSoTimeout(20000); // 设置超时时间
//        System.out.println("ss");

        // 获取输入流与输出流
        // 读来自服务器的信息
        InputStream inputStream = socket.getInputStream();
        // 向套接字写数据
        OutputStream outputStream = socket.getOutputStream();

        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8189));
    }
}
