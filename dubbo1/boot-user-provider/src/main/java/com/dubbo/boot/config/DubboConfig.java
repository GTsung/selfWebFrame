package com.dubbo.boot.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author GTsung
 * @date 2021/10/11
 */
@Configuration
public class DubboConfig {

    // dubbo.application.name=boot-user-provider
//    @Bean
//    public ApplicationConfig applicationConfig() {
//        ApplicationConfig config = new ApplicationConfig();
//        config.setName("boot-user-provider");
//        return config;
//    }

    /**
     * dubbo.registry.address=127.0.0.1:2181
     * dubbo.registry.protocol=zookeeper
     * @return
     */
//    @Bean
//    public RegistryConfig registryConfig() {
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setAddress("127.0.0.1:2181");
//        registryConfig.setProtocol("zookeeper");
//        return registryConfig;
//    }

    /**
     * dubbo.protocol.name=dubbo
     * dubbo.protocol.port=20880
     * @return
     */
//    @Bean
//    public ProtocolConfig protocolConfig() {
//        ProtocolConfig protocolConfig = new ProtocolConfig();
//        protocolConfig.setName("dubbo");
//        protocolConfig.setPort(20880);
//        return protocolConfig;
//    }
}
