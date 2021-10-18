package com.self.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @date 2018/10/31
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtil {

    private static final String ONE = "1";

    /**
     * 左、右半角&全角括号
     */
    private static final String HALF_L_PARENTHESES = "\\(";
    private static final String HALF_R_PARENTHESES = "\\)";
    private static final String FULL_L_PARENTHESES = "（";
    private static final String FULL_R_PARENTHESES = "）";

    /**
     * 将传入对象的String类型属性赋值为空字符串（""）
     *
     * @param o
     */
    public static void fulfillStringField(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(o);
                if (null != value) {
                    continue;
                }
                if (String.class.isAssignableFrom(field.getType())) {
                    field.set(o, "");

                }
            }
        } catch (Exception e) {
            log.error("赋值失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 获取所有属性
     *
     * @param clz
     * @return
     */
    public static List<Field> getAllFields(Class clz) {
        List<Field> fieldList = Lists.newArrayList();
        Class tempClass = clz;
        while (tempClass != null) {
            //当父类为null的时候说明到达了最上层的父类(Object类)
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            //得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }

    /**
     * 将实体类转为map
     *
     * @param obj
     * @param columns
     * @return
     */
    public static Map<String, Object> entityToMap(Object obj, List<String> columns) {
        Map<String, Object> map = Maps.newHashMap();
        if (null == obj) {
            return map;
        }
        Class clz = obj.getClass();
        List<Field> fields = getAllFields(clz);
        for (Field field : fields) {
            if (!columns.contains(field.getName())) {
                // 如果不是需要记录的字段，不做记录
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (null != value) {
                    map.put(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                log.error("获取字段{}值失败：{}", field.getName(), e);
            }
        }
        return map;
    }

    /**
     * 判断两个值是否相等
     *
     * @param obj1
     * @param obj2
     * @param <T>
     * @return
     */
    public static <T> boolean equals(T obj1, T obj2) {
        if (null == obj1) {
            if (null == obj2) {
                // 两个值都为null，认为相等
                return true;
            }
            return false;
        } else {
            return obj1.equals(obj2);
        }
    }

    /**
     * String比较,空字符串与null为相等,比较文字
     * @param s1
     * @param s2
     * @return
     */
    public static boolean wordsEqualsNoCheckNull(String s1, String s2) {
        if (StringUtils.isBlank(s1) && StringUtils.isBlank(s2)) {
            return true;
        }
        if (StringUtils.isBlank(s1) && StringUtils.isNotBlank(s2)
                || StringUtils.isNotBlank(s1) && StringUtils.isBlank(s2)) {
            return false;
        }
        return s1.trim().equals(s2.trim());
    }


    /**
     * 将异常堆栈信息转为string
     *
     * @param e
     * @return
     */
    public static String exceptionStack2String(Exception e) {
        // 处理保存报错堆栈信息
        StringBuilder exceptionBuilder = new StringBuilder(e.toString()).append("\n");
        StackTraceElement[] traceElements = e.getStackTrace();
        if (null != traceElements && traceElements.length > 0) {
            for (StackTraceElement traceElement : traceElements) {
                exceptionBuilder.append("\tat ").append(traceElement.toString()).append("\n");
            }
        }
        return exceptionBuilder.toString();
    }

    public static Object getFieldValue(String fieldName, Object obj) {
        if (null == obj) {
            return "";
        }
        List<Field> fieldList = getAllFields(obj.getClass());
        for (Field field : fieldList) {
            field.setAccessible(true);
            if (field.getName().equalsIgnoreCase(fieldName)) {
                try {
                    Object value = field.get(obj);
                    return null == value ? "" : value;
                } catch (Exception e) {
                    log.info("从{}获取字段{}值失败：{}", obj, fieldName, e);
                }
            }
        }
        return "";
    }

    /**
     * 获取field
     *
     * @param fieldName
     * @param clz
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getField(String fieldName, Class clz) throws NoSuchFieldException {
        List<Field> fieldList = getAllFields(clz);
        for (Field field : fieldList) {
            if (field.getName().equalsIgnoreCase(fieldName)) {
                return field;
            }
        }
        throw new NoSuchFieldException(fieldName);
    }


    public static <T> T getByCode(Class<T> clz, Object code) {
        try {
            Method method = clz.getMethod("getCode");
            for (T t1 : clz.getEnumConstants()) {
                Object realCode = method.invoke(t1);
                if (realCode.equals(code)) {
                    return t1;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.info("获取反射字段异常", e);
        }
        return null;
    }

    public static boolean validate(Class clz, Object code) {
        return null != getByCode(clz, code);
    }

    /**
     * 转大写
     * @param source
     * @return
     */
    public static String toUpperCase(String source) {
        if (null == source) {
            return null;
        }
        return source.toUpperCase();
    }
}
