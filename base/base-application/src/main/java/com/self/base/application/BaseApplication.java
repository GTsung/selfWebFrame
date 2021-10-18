package com.self.base.application;

import com.self.common.utils.SpringFactoryUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author GTsung
 * @date 2021/9/8
 */

@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableTransactionManagement
@MapperScan(basePackages = {"com.self.base.biz.mapper"})
@SpringBootApplication(scanBasePackages = {"com.self.base", "com.self.common.config"})
public class BaseApplication {

    public static void main(String[] args) {
        SpringFactoryUtil.setApplicationContext(SpringApplication.run(BaseApplication.class, args));
    }
}
