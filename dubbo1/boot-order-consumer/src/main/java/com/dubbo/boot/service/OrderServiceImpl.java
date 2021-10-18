package com.dubbo.boot.service;

import com.dubbo.gmall.domain.UserAddress;
import com.dubbo.gmall.inter.OrderService;
import com.dubbo.gmall.inter.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/10/11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @DubboReference
    private UserService userService;

    @Override
    public void initOrder(String userId) {
        System.out.println("传输userId=" + userId);
        List<UserAddress> userAddresses = userService.getUserAddressList(userId);
        for (UserAddress u : userAddresses) {
            System.out.println(u.getUserAddress() + "==" + u.getPhoneNum());
        }

    }
}
