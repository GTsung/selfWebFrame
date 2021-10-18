package com.dubbo.gmall.service;

import com.dubbo.gmall.domain.UserAddress;
import com.dubbo.gmall.inter.OrderService;
import com.dubbo.gmall.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/10/11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    UserService userService;

    @Override
    public void initOrder(String userId) {
        System.out.println("传输userId=" + userId);
        List<UserAddress> userAddresses = userService.getUserAddressList(userId);
        for (UserAddress u : userAddresses) {
            System.out.println(u.getUserAddress() + "==" + u.getPhoneNum());
        }

    }
}
