package com.self.common.rpc.demo;

import java.io.IOException;

public interface Server {

    void start() throws IOException;

    void register(Class serviceInterface, Class impl);

    boolean isRunning();

    int getPort();

    void stop();
}
