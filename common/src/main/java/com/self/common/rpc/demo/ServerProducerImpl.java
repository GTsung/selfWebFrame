package com.self.common.rpc.demo;

/**
 * @author GTsung
 * @date 2021/9/6
 */
public class ServerProducerImpl implements ServerProducer {

    @Override
    public String sendData(String data) {
        return "I'm serverProducer, the data is " + data;
    }
}
