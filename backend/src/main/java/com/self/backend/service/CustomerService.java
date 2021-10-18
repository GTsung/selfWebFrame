package com.self.backend.service;

import com.self.backend.domain.CustomerDO;
import com.self.backend.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    int saveCustomer(CustomerDTO customerDTO);

    List<CustomerDO> getAllCustomers();
}
