package com.self.common.config.cache;

import com.self.common.utils.JsonUtil;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

/**
 * @author GTsung
 * @date 2021/8/19
 */
@Component
public class SpringCacheKeyGenerator implements KeyGenerator {
    private final static int NO_PARAM_KEY = 0;
    private final static String sp = ":";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder keyBuilder = new StringBuilder(30);
        // 类名
        keyBuilder.append(method.getDeclaringClass().getName());
        keyBuilder.append(sp);
        // 方法名
        keyBuilder.append(method.getName());
        keyBuilder.append(sp);
        if (params.length > 0) {
            // 参数值
            for (Object object : params) {
                if (isSimpleValueType(object.getClass())) {
                    keyBuilder.append(object);
                } else {
                    keyBuilder.append(JsonUtil.toJson(object).hashCode());
                }
            }
        } else {
            keyBuilder.append(NO_PARAM_KEY);
        }
        return keyBuilder.toString();
    }

    public static boolean isSimpleValueType(Class<?> clazz) {
        return (ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isEnum() || CharSequence.class.isAssignableFrom(clazz)
                || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class == clazz
                || URL.class == clazz || Locale.class == clazz || Class.class == clazz);
    }
}

