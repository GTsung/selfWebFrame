package com.self.net;

import java.io.IOException;
import java.net.*;

/**
 * @author GTsung
 * @date 2021/10/14
 */
public class UdpServerTest {

    public static void main(String[] args) throws IOException {
        DatagramSocket sendSocket = new DatagramSocket(8189);
        // 要发送的数据
        byte[] bytes = "卡萨丁".getBytes();
        // 创建发送包，指定发送端口
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("localhost"), 8011);
        // 发送
        sendSocket.send(packet);
        // 接收
        byte[] receive = new byte[1024];
        DatagramPacket receiveData = new DatagramPacket(receive, receive.length);
        sendSocket.receive(receiveData);
        String data = new String(receiveData.getData(), 0, receiveData.getLength());
        System.out.println(data);
        sendSocket.close();
    }

}
