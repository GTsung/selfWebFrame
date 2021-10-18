package com.self.base.biz.manager;

import com.self.base.biz.mapper.AddressDao;
import com.self.base.client.domain.AddressDO;
import com.self.common.base.BaseManager;
import com.self.common.builder.ConditionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/9/8
 */
@Component
public class AddressManager extends BaseManager<AddressDO, ConditionBuilder> {

    @Autowired
    private AddressDao addressDao;

    public List<AddressDO> getAll() {
        return addressDao.selectAll();
    }
}
