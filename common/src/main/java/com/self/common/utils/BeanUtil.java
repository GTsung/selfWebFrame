package com.self.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtil {

    /**
     * beancopy工具， 只 copy 类型及字段名都一致的字段
     * @param source
     * @param target
     */
    public static  <T> T copy(Object source, T target) {
        if(source == null){
            return target;
        }
        BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
        beanCopier.copy(source, target, null);
        return target;
    }
}
