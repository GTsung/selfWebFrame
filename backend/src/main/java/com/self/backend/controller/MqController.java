package com.self.backend.controller;

import com.self.common.mq.boot.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author GTsung
 * @date 2021/8/24
 */
@Slf4j
@RequestMapping("/mq")
@RestController
public class MqController {

    @GetMapping("/{msg}")
    public Object send(@PathVariable("msg") String msg) {
        MsgUtil.send2Mq("topic_demo_exchange", "s.*", msg);
        return true;
    }
}
