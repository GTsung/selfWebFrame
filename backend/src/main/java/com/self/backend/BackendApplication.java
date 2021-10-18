package com.self.backend;

import com.self.common.utils.SpringFactoryUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author GTsung
 * @date 2021/8/24
 */
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.self.base.client.feigns"})
@SpringBootApplication(scanBasePackages = {"com.self"})
@MapperScan(basePackages = {"com.self.backend.mapper"})
public class BackendApplication {

    public static void main(String[] args) {
        SpringFactoryUtil.setApplicationContext(SpringApplication.run(BackendApplication.class, args));
    }
}
