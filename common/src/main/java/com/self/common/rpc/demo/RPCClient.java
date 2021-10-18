package com.self.common.rpc.demo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author GTsung
 * @date 2021/9/6
 */
public class RPCClient {

    public static <T> T getRemoteProxyObj(final Class<?> serviceInterface, final InetSocketAddress addr) {
        return (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), new Class[]{serviceInterface},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = null;
                        ObjectOutputStream out = null;
                        ObjectInputStream in = null;
                        try {
                            // 创建socket客户端，连接到服务端
                            socket = new Socket();
                            socket.connect(addr);
                            // 将远程服务调用所需的方法及参数发送给服务端
                            out = new ObjectOutputStream(socket.getOutputStream());
                            out.writeUTF(serviceInterface.getName());
                            out.writeUTF(method.getName());
                            out.writeObject(method.getParameterTypes());
                            out.writeObject(args);
                            // 等待服务器返回
                            in = new ObjectInputStream(socket.getInputStream());
                            return in.readObject();
                        } finally {
                            if (socket != null) {
                                socket.close();
                            }
                            if (out != null) {
                                out.close();
                            }
                            if (in != null) {
                                in.close();
                            }
                        }
                    }
                });
    }
}
