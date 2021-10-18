package com.self.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author GTsung
 * @date 2021/10/12
 */
public class ServerSocketTest {

    public static void main(String[] args) throws IOException {
        // 创建服务端的socket，监听8189端口
        ServerSocket serverSocket = new ServerSocket(8189);
        // 获取ip地址
        InetAddress inetAddress = serverSocket.getInetAddress();
        System.out.println(inetAddress.getHostAddress());
        // 获取本地端口即8189
        System.out.println(serverSocket.getLocalPort());
        // 获取本地网络地址
        InetSocketAddress inetSocketAddress = (InetSocketAddress) serverSocket.getLocalSocketAddress();
        System.out.println(inetSocketAddress.getHostName()+":"+inetSocketAddress.getPort());
        // 绑定，只能绑定一次，第一行已绑定一个端口，此处会报错
//        serverSocket.bind(new InetSocketAddress(8190));
        // 等待连接，会一直阻塞当前线程直到连接完毕
        Socket socket = serverSocket.accept();
        // 获取客户端的输入
        InputStream inputStream = socket.getInputStream();
    }
}
