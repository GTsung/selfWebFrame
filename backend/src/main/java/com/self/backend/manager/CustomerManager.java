package com.self.backend.manager;

import com.self.common.base.BaseManager;
import com.self.backend.domain.CustomerDO;
import com.self.backend.mapper.CustomerDao;
import com.self.common.builder.ConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Component
public class CustomerManager extends BaseManager<CustomerDO, ConditionBuilder> {

    @Autowired
    private CustomerDao customerDao;

    public List<CustomerDO> getAll() {
        return customerDao.selectAll();
    }
}
