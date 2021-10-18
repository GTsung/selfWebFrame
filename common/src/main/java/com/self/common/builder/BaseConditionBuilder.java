package com.self.common.builder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.self.common.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;

import java.util.*;

/**
 * @author GTsung
 * @date 2021/8/30
 */
public class BaseConditionBuilder<T extends BaseConditionBuilder> {

    /**
     * 条件为equals
     */
    protected Map<String, Object> equalsMap;

    /**
     * 条件为in
     */
    protected Map<String, List> inMap;

    /**
     * 条件为like 后缀加%
     */
    protected Map<String, String> likeMap;

    /**
     * 属性为null
     */
    protected List<String> nullList;

    /**
     * between
     */
    protected Map<String, List<Object>> betweenMap;

    /**
     * 查询字段
     */
    protected String[] properties;

    /**
     * 唯一
     */
    protected boolean distinct;

    /**
     * 升序字段
     */
    protected String asc;

    /**
     * 降序字段
     */
    protected String desc;

    private Class clazz;

    public T init(Class clazz) {
        initBase(clazz);
        return (T) this;
    }

    protected void initBase(Class clazz) {
        this.clazz = clazz;
        this.equalsMap = Maps.newHashMap();
        this.betweenMap = Maps.newHashMap();
        this.inMap = Maps.newHashMap();
        this.likeMap = Maps.newHashMap();
        this.nullList = Lists.newArrayList();
    }

    public BaseConditionBuilder() {
    }

    public T andLike(String prop, String value) {
        if (!StringUtils.isEmpty(value)) {
            likeMap.put(prop, value + "%");
        }
        return (T) this;
    }


    public T andEqualTo(String field, Object obj) {
        if (Objects.nonNull(obj)) {
            equalsMap.put(field, obj);
        }
        return (T) this;
    }

    public T andIsNull(String prop) {
        if(StringUtils.isNotEmpty(prop) && !nullList.contains(prop)) {
            nullList.add(prop);
        }
        return (T)this;
    }

    public T andIn(String field, List objs) {
        if (CollectionUtils.isEmpty(objs)) {
            inMap.put(field, objs);
        }
        return (T) this;
    }

    public T delFlag(Integer delFlag) {
        if (null != delFlag) {
            equalsMap.put("delFlag", delFlag);
        }
        return (T) this;
    }

    public T createTime(Date createTime) {
        if (null != createTime) {
            equalsMap.put("createTime", createTime);
        }
        return (T) this;
    }

    public T createUser(String createUser) {
        if (!StringUtils.isEmpty(createUser)) {
            equalsMap.put("createUser", createUser);
        }
        return (T) this;
    }

    public T updateTime(Date updateTime) {
        if (null != updateTime) {
            equalsMap.put("updateTime", updateTime);
        }
        return (T) this;
    }

    public T updateUser(String updateUser) {
        if (!StringUtils.isEmpty(updateUser)) {
            equalsMap.put("updateUser", updateUser);
        }
        return (T) this;
    }

    public T asc(String ascField) {
        if (!StringUtils.isEmpty(ascField)) {
            this.asc = ascField;
        }
        return (T) this;
    }

    public T desc(String descField) {
        if (!StringUtils.isEmpty(descField)) {
            this.desc = descField;
        }
        return (T) this;
    }

    public T properties(String... properties) {
        if (null != properties && properties.length > 0) {
            this.properties = properties;
        }
        return (T) this;
    }

    public T distinct() {
        this.distinct = true;
        return (T) this;
    }

    /**
     * between查询条件构造
     *
     * @param prop
     * @param start
     * @param end
     * @return
     */
    public T between(String prop, Object start, Object end) {
        AssertUtil.assertNotBlank(prop, "between字段不能为空");
        if (null != start || null != end) {
            // 如果存在一个条件不为空，两个条件均不能为空
            AssertUtil.assertNotNull(start, "between开始条件（start）不能为空");
            AssertUtil.assertNotNull(end, "between结束条件不能为空");
            betweenMap.put(prop, Arrays.asList(start, end));
        }
        return (T) this;
    }

    public Condition build() {
        Condition condition = ConditionBuilderHelper.build(clazz, equalsMap, inMap, likeMap, betweenMap, asc, desc);
        // 需要选择的属性
        if (null != properties && properties.length > 0) {
            condition.selectProperties(properties);
        }
        // 是否去重
        condition.setDistinct(distinct);
        // 增加为空的条件
        nullList.forEach(p ->
                condition.and()
                        .andIsNull(p)
        );
        return condition;
    }

}
