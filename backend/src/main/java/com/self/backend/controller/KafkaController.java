package com.self.backend.controller;

import com.self.common.kafka.boot.KfkConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GTsung
 * @date 2021/12/3
 */
@RestController
@RequestMapping("/kfk")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/send")
    public String send() {
        kafkaTemplate.send(KfkConstant.TOPIC_NAME,
                0, "key", "data");
        return "OK";
    }
}
