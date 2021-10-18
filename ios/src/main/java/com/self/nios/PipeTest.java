package com.self.nios;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

/**
 * @author GTsung
 * @date 2021/10/14
 */
public class PipeTest {

    public static void main(String[] args) throws IOException {
        WritableByteChannel out = Channels.newChannel(System.out);
        ReadableByteChannel workChannel = startWorker(10);
        ByteBuffer buffer = ByteBuffer.allocate(100);
        while (workChannel.read(buffer) >= 0) {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }
    }

    private static ReadableByteChannel startWorker(int reps) throws IOException {
        Pipe pipe = Pipe.open();
        Worker worker = new Worker(pipe.sink(), reps);
        worker.start();
        return pipe.source();
    }

    private static class Worker extends Thread {
        WritableByteChannel channel;
        private int reps;

        Worker(WritableByteChannel channel, int reps) {
            this.channel = channel;
            this.reps = reps;
        }


        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(100);
            try {
                for (int i = 0; i < this.reps; i++) {
                    doSomeWork(buffer);
                    while (channel.write(buffer) > 0) {

                    }
                }
                this.channel.close();
            } catch (Exception e) {
            }
        }
        private String[] pro = {"no", "sada", "sd"};
        private Random random = new Random();
        private void doSomeWork(ByteBuffer buffer) {
            int p = random.nextInt(pro.length);
            buffer.clear();
            buffer.put(pro[p].getBytes());
            buffer.put("\r\n".getBytes());
            buffer.flip();
        }
    }
}
