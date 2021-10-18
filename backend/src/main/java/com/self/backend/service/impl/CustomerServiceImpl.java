package com.self.backend.service.impl;

import com.self.backend.domain.CustomerDO;
import com.self.backend.dto.CustomerDTO;
import com.self.backend.manager.CustomerManager;
import com.self.backend.service.CustomerService;
import com.self.common.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerManager customerManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveCustomer(CustomerDTO customerDTO) {
        return customerManager.save(BeanUtil.copy(customerDTO, CustomerDO.builder().build()));
    }

    @Override
    public List<CustomerDO> getAllCustomers() {
        return customerManager.getAll();
    }
}
