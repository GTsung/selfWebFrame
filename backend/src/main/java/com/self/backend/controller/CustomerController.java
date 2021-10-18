package com.self.backend.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.self.common.anno.OperationResult;
import com.self.common.anno.PageAnn;
import com.self.backend.domain.CustomerDO;
import com.self.backend.dto.CustomerDTO;
import com.self.common.res.SelfResult;
import com.self.backend.service.CustomerService;
import com.self.common.utils.BeanUtil;
import com.self.backend.vo.CustomerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Slf4j
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @OperationResult
    @PostMapping("/save")
    public Object saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @PageAnn
    @GetMapping("/get")
    public Object getAllCustomers(CustomerVO customerVO) {
        return customerService.getAllCustomers();
    }

    @GetMapping
    public Object getAll(CustomerVO customerVO) {
        PageHelper.startPage(customerVO.getPageIndex(), customerVO.getPageSize());
        List<CustomerDO> customerDOS = customerService.getAllCustomers();
        PageInfo<CustomerDO> page = PageInfo.of(customerDOS);
        return SelfResult.SUCCESS((int) page.getTotal(), dealResult(customerDOS));
    }

    private List<CustomerDTO> dealResult(List<CustomerDO> customerDOS) {
        return customerDOS
                .stream()
                .map(c -> BeanUtil.copy(c, CustomerDTO.builder().build()))
                .collect(Collectors.toList());
    }

}
