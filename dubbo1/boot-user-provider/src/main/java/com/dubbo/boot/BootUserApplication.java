package com.dubbo.boot;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author GTsung
 * @date 2021/10/11
 */
@EnableDubbo // 开启dubbo注解
@SpringBootApplication
public class BootUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootUserApplication.class, args);
    }
}
