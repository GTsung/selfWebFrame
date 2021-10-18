package com.self.base.biz.service.impl;

import com.self.base.biz.manager.AddressManager;
import com.self.base.biz.service.AddressService;
import com.self.base.client.domain.AddressDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/9/8
 */
@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressManager addressManager;

    @Override
    public List<AddressDO> getAll() {
        return addressManager.getAll();
    }
}
