package com.self.nios;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author GTsung
 * @date 2021/10/14
 */
public class ChannelTestTwo {

    public static void main(String[] args) throws IOException {
        ReadableByteChannel r = Channels.newChannel(System.in);
        WritableByteChannel w = Channels.newChannel(System.out);
        channelCopy1(r, w);
        channelCopy2(r, w);
        r.close();
        w.close();
    }

    private static void channelCopy2(ReadableByteChannel r, WritableByteChannel w) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16 * 1024);
        while (r.read(byteBuffer) != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                w.write(byteBuffer);
            }
            byteBuffer.clear();
        }
    }

    private static void channelCopy1(ReadableByteChannel r, WritableByteChannel w) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(16 * 1024);
        while (r.read(byteBuffer) != -1) {
            // 准备写
            byteBuffer.flip();
            w.write(byteBuffer);
            byteBuffer.compact();
        }
        byteBuffer.flip();
        while(byteBuffer.hasRemaining()) {
            w.write(byteBuffer);
        }
    }
}
