package com.self.base.application.controller;

import com.self.base.biz.service.AddressService;
import com.self.base.client.domain.AddressDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/9/8
 */
@Slf4j
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/address/all")
    public List<AddressDO> getAll() {
        return addressService.getAll();
    }
}
