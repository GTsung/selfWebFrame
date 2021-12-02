package com.self.select;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author GTsung
 * @date 2021/11/21
 */
public class Handler implements Runnable {

    private volatile static Selector selector;

    private final SocketChannel socketChannel;

    private SelectionKey key;

    private volatile ByteBuffer input = ByteBuffer.allocate(1024);
    private volatile ByteBuffer output = ByteBuffer.allocate(1024);

    public Handler(SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        selector = Selector.open();
        key = socketChannel.register(selector, SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        try {
            while (selector.isOpen() && socketChannel.isOpen()) {
                Set<SelectionKey> keys = select();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        read(key);
                    } else if (key.isWritable()) {
                        write(key);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write(SelectionKey key) throws IOException {
        output.flip();
        if (socketChannel.isOpen()) {
            socketChannel.write(output);
            key.channel();
            socketChannel.close();
            output.clear();
        }
    }

    private void read(SelectionKey key) throws IOException {
        socketChannel.read(input);
        if (input.position() == 0) {
            return;
        }
        input.flip();
        process();
        input.clear();
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void process() {
        byte[] bytes = new byte[input.remaining()];
        input.get(bytes);
        String msg = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("receive msg  from client:" + msg);
        output.put("hello client".getBytes());
    }

    private Set<SelectionKey> select() throws IOException {
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        if (keys.isEmpty()) {
            int interestOps = key.interestOps();
            selector = Selector.open();
            key = socketChannel.register(selector, interestOps);
            return select();
        }
        return keys;
    }
}
