package com.self.net;

import java.net.InetAddress;

/**
 * @author GTsung
 * @date 2021/10/12
 */
public class InetAddressTest {
    public static void main(String[] args) throws Exception {
        // 获取域名的所有ip地址
        InetAddress[] inetAddresses = InetAddress.getAllByName("www.google.com");
//        InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
        for (InetAddress address: inetAddresses) {
            System.out.println(address.getHostAddress()); // 十进制的ip地址
            byte[] bytes = address.getAddress(); // 获取数字的ip地址
            System.out.println(address.getHostName()); // hostname
        }
        // 为本地主机创建一个InetAddress
        InetAddress local = InetAddress.getLocalHost();
    }
}
