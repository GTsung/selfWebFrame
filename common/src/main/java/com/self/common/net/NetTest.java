package com.self.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author GTsung
 * @date 2021/9/21
 */
public class NetTest {

    public static void main(String[] args) throws IOException {
//        Socket socket = new Socket("192.1.1.1", 80);
//        socket = new Socket();
//        socket.connect(new InetSocketAddress("192.1.1.1", 90), 12);
//        socket.getOutputStream(); // 获取输出流，用于给服务端传递数据
//        socket.getInputStream(); // 获取输入流，接收服务端发送的数据

//        // 根据域名获取ip
//        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
//        for (InetAddress a: addresses) {
//            System.out.println(a.getHostAddress());
//        }
//        // 获取本地ip
//        System.out.println(InetAddress.getLocalHost());

        // 服务端的套接字,负责监控端口为8189的服务器
        ServerSocket server = new ServerSocket(8189);
        Socket s = server.accept(); // 不停等待直到客户端连接此端口
        InputStream inputStream = s.getInputStream(); // 从客户端传入的
        OutputStream outputStream = s.getOutputStream(); // 用于传输给客户端的
    }
}
