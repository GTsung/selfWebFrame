package com.self.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author GTsung
 * @date 2021/10/14
 */
public class UdpClientTest {

    public static void main(String[] args) throws IOException {
        DatagramSocket receiveSocket = new DatagramSocket(8011);
        while (true) {
            byte[] bt = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(bt, bt.length);
            receiveSocket.receive(receivePacket);
            String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println(msg);
            //通过数据包也可以解析ip、和端口,打印
            InetAddress ipAddress = receivePacket.getAddress();
            int receivePort = receivePacket.getPort();
            System.out.println(ipAddress);
            System.out.println(receivePort);
            //发送数据
            String aaString = "123";
            byte[] bb = aaString.getBytes();
            //创建发送类型的数据包
            DatagramPacket sendPscket = new DatagramPacket(bb, bb.length,InetAddress.getByName("localhost"), 8189);
            receiveSocket.send(sendPscket);
        }
    }
}
