package com.self.backend.service.impl;

import com.self.backend.domain.CommonConfigDO;
import com.self.backend.manager.CommonConfigManager;
import com.self.backend.service.CommonConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/8/19
 */
@Slf4j
@Service
public class CommonConfigServiceImpl implements CommonConfigService {

    @Autowired
    private CommonConfigManager commonConfigManager;

    @Override
    @Transactional
    public int save(CommonConfigDO commonConfigDO) {
        return commonConfigManager.save(commonConfigDO);
    }

    @Override
    public List<CommonConfigDO> selectByGroup(String group) {
        return commonConfigManager.selectByGroup(group);
    }

    @Override
    public CommonConfigDO selectByGroupAndKey(String group, String key) {
        return commonConfigManager.selectByGroupAndKey(group, key);
    }

    @Override
    public CommonConfigDO selectByKey(String key) {
        return commonConfigManager.selectByKey(key);
    }

    @Override
    public int update(CommonConfigDO commonConfigDO) {
        return commonConfigManager.update(commonConfigDO);
    }
}
