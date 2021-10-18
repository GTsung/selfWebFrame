package com.self.common.rpc.demo;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author GTsung
 * @date 2021/9/6
 */
public class RPCTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server server = new ServerCenter(8088);
                    server.register(ServerProducer.class, ServerProducerImpl.class);
                    server.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        ServerProducer producer = RPCClient.getRemoteProxyObj(ServerProducer.class,
                new InetSocketAddress("localhost", 8088));
        System.out.println(producer.sendData("da"));
    }
}
