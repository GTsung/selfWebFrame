package com.self.backend.controller;

import com.self.base.client.feigns.AddressService;
import com.self.common.anno.OperationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GTsung
 * @date 2021/9/8
 */
@Slf4j
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @OperationResult
    @GetMapping
    public Object getAllAddresses() {
        return addressService.getAll();
    }

}
