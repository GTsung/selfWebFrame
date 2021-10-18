package com.dubbo.gmall.service;

import com.dubbo.gmall.domain.UserAddress;
import com.dubbo.gmall.inter.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * @author GTsung
 * @date 2021/10/11
 */
public class UserServiceImpl implements UserService {

    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress userAddress1 = UserAddress.builder().id(1)
                .phoneNum("13390001111").userAddress("北京中南海")
                .userId("0001").build();
        UserAddress userAddress2 = UserAddress.builder().id(2)
                .phoneNum("19880011111").userAddress("钟鼓楼")
                .userId("0002").build();
        return Arrays.asList(userAddress1, userAddress2);
    }
}
