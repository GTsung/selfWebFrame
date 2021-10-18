package com.dubbo.gmall.inter;

import com.dubbo.gmall.domain.UserAddress;

import java.util.List;

public interface UserService {

    List<UserAddress> getUserAddressList(String userId);
}
