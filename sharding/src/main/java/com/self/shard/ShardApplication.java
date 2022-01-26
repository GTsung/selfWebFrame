package com.self.shard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author GTsung
 * @date 2022/1/26
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.self.shard.mapper"})
public class ShardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardApplication.class, args);
    }

}
