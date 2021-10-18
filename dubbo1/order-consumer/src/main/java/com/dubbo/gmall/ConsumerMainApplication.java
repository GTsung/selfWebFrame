package com.dubbo.gmall;

import com.dubbo.gmall.inter.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author GTsung
 * @date 2021/10/11
 */
public class ConsumerMainApplication {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext ioc= new ClassPathXmlApplicationContext("consumer.xml");
        OrderService orderService = ioc.getBean(OrderService.class);
        orderService.initOrder("12");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
