package com.self.common.base;

import com.self.common.builder.BaseConditionBuilder;
import com.self.common.enums.DelFlagEnum;
import com.self.common.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Objects;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Slf4j
public class BaseManager<T extends BaseDO, B extends BaseConditionBuilder> {

    @Autowired
    private BaseDao<T> dao;

    private Class clazz;

    private Class bClazz;

    public BaseManager() {
        Type[] clzs = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        AssertUtil.assertTrue(null != clzs && clzs.length >= 2, "泛型参数传入异常");
        clazz = (Class) clzs[0];
        bClazz = (Class) clzs[1];
    }

    public int save(T model) {
        String operator = getOperator(model.getCreateUser());
        return this.save(model, operator);
    }

    private String getOperator(String createUser) {
        return StringUtils.isBlank(createUser) ? "admin" : createUser;
    }

    public int save(T model, String operator) {
        if (Objects.isNull(model)) {
            return 0;
        }
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setCreateUser(operator);
        model.setUpdateUser(operator);
        model.setDelFlag(DelFlagEnum.PERSISTENT.getCode());
        return dao.insert(model);
    }

    public int updateByExampleSelective(T model, Example example) {
        String operator = getOperator(model.getUpdateUser());
        return this.updateByExampleSelective(model, example, operator);
    }

    public int updateByExampleSelective(T model, Example example, String operator) {
        AssertUtil.assertTrue(Objects.nonNull(model) && Objects.nonNull(example), "根据条件更新时实体类和条件不能为空");
        model.setUpdateTime(new Date());
        try {
            // 这里把创建人置空，防止把创建人覆盖掉
            model.setCreateUser(null);
            model.setUpdateUser(operator);
        } catch (Exception e) {
            log.info("设置更新人失败，预计为mq消息发起的更新，请检查：{}", e);
        }
        return dao.updateByExampleSelective(model, example);
    }

    final protected B getConditionBuilder() {
        return getConditionBuilder(clazz);
    }

    final protected B getConditionBuilder(Class clazz) {
        B conditionBuilder = null;
        try {
            conditionBuilder = (B) bClazz.newInstance();
            conditionBuilder = (B) bClazz.getMethod("init",  Class.class).invoke(conditionBuilder, clazz);
        } catch (Exception e) {
            log.info("失败");
        }
        return conditionBuilder;
    }

}
