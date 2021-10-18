package com.self.backend.manager;

import com.self.common.base.BaseManager;
import com.self.backend.domain.CommonConfigDO;
import com.self.common.builder.ConditionBuilder;
import com.self.common.enums.DelFlagEnum;
import com.self.backend.mapper.CommonConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author GTsung
 * @date 2021/8/19
 */
@Component
public class CommonConfigManager extends BaseManager<CommonConfigDO, ConditionBuilder> {

    @Autowired
    private CommonConfigDao commonConfigDao;

    public List<CommonConfigDO> selectByGroup(String group) {
        Condition condition = new Condition(CommonConfigDO.class);
        condition.and().andEqualTo("group", group)
                .andEqualTo("delFlag", DelFlagEnum.PERSISTENT.getCode());
        return commonConfigDao.selectByCondition(condition);
    }

    public CommonConfigDO selectByGroupAndKey(String group, String key) {
        Example example = new Example(CommonConfigDO.class);
        example.and().andEqualTo("group", group)
                .andEqualTo("key", key)
                .andEqualTo("delFlag", DelFlagEnum.PERSISTENT.getCode());
        return commonConfigDao.selectOneByExample(example);
    }

    public CommonConfigDO selectByKey(String key) {
        Condition condition = new Condition(CommonConfigDO.class);
        condition.and().andEqualTo("key", key)
                .andEqualTo("delFlag", DelFlagEnum.PERSISTENT.getCode());
        return commonConfigDao.selectByCondition(condition).stream().findFirst().orElse(null);
    }

    public int update(CommonConfigDO commonConfigDO) {
        Condition condition = new Condition(CommonConfigDO.class);
        condition.and().andEqualTo("delFlag", DelFlagEnum.PERSISTENT.getCode())
                .andEqualTo("id", commonConfigDO.getId());
        return commonConfigDao.updateByCondition(commonConfigDO, condition);
    }
}
