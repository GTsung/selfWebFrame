package com.self.backend.controller;

import com.self.common.anno.OperationResult;
import com.self.backend.service.BusinessService;
import com.self.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author GTsung
 * @date 2021/8/18
 */
@RestController
@RequestMapping("redis/test")
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BusinessService businessService;

    @OperationResult
    @PutMapping
    public Object setKey(@RequestParam("key") String key, @RequestParam("value") String value) {
//        return redisUtil.set(key, value);
        redisUtil.set(key, value);
        return redisUtil.expire(key, 60);
    }

    @OperationResult
    @GetMapping
    public Object getKey(String key) {
        String val = (String) redisUtil.getKey(key);
        long time = redisUtil.getExpireTime(key);
        return val + "=" + time;
    }

    @OperationResult
    @PutMapping("incr")
    public Object incr(String key, Long delta) {
        return redisUtil.incr(key, delta);
    }

    @OperationResult
    @PutMapping("/zset/{zSetName}/{value}/{expire}")
    public Object zSet(@PathVariable("zSetName") String zSetName,
                       @PathVariable("value") String value, @PathVariable("expire") long expire) {
        return redisUtil.zsSet(zSetName, value, System.currentTimeMillis() + expire * 1000);
    }

    @OperationResult
    @GetMapping("/zset/{zSetName}")
    public Object getZSet(@PathVariable("zSetName") String zSetName) {
        Set<Object> expireSet = redisUtil.zsRangeByScore(zSetName, 0, System.currentTimeMillis());
        return expireSet;
    }


    @OperationResult
    @PutMapping("/business/wfq")
    public Object wfq() {
        businessService.doWfq();
        return true;
    }

    @OperationResult
    @PutMapping("/business/submit")
    public Object submit() {
        businessService.doSubmit();
        return true;
    }
}
