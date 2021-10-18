package com.self.common.utils;

import com.self.common.enums.LogLevelEnum;
import com.self.common.enums.ResultEnum;
import com.self.common.exception.SelfException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertUtil {



    /**
     * 非空校验
     *
     * @param o
     * @param msg
     */
    public static void assertNotNull(Object o, String msg) {
        assertTrue(null != o, msg);
    }

    public static void assertNotNull(Object o, ResultEnum code, String msg) {
        assertTrue(null != o, code, msg);
    }

    public static void assertNotNull(Object o, String format, Object... args) {
        assertNotNull(o, String.format(format, args));
    }

    public static void assertNotNull(Object o, ResultEnum code, String format, Object... args) {
        assertNotNull(o, code, String.format(format, args));
    }

    /**
     * 空值校验
     *
     * @param o
     * @param format
     * @param args
     */
    public static void assertNull(Object o, String format, Object... args) {
        assertNull(o, String.format(format, args));
    }

    public static void assertNull(Object o, String msg) {
        assertTrue(null == o, msg);
    }

    /**
     * 如果condition不是true则报错
     *
     * @param condition
     * @param msg
     */
    public static void assertTrue(boolean condition, String msg) {
        assertTrue(condition, ResultEnum.ERROR_10005, msg);
    }

    public static void assertTrue(boolean condition, ResultEnum codeEnum, String msg) {
        assertTrue(condition, codeEnum, LogLevelEnum.INFO, msg);
    }

    public static void assertTrue(boolean condition, ResultEnum codeEnum, LogLevelEnum logLevel, String msg) {
        if (!condition) {
            throw new SelfException(codeEnum.getCode(), msg, logLevel);
        }
    }

    public static void assertTrue(boolean condition, String format, Object... args) {
        assertTrue(condition, String.format(format, args));
    }

    public static void assertTrue(boolean condition, ResultEnum code, String format, Object... args) {
        assertTrue(condition, code, String.format(format, args));
    }

    public static void assertTrue(boolean condition, ResultEnum code, LogLevelEnum logLevel, String format, Object... args) {
        assertTrue(condition, code, logLevel, String.format(format, args));
    }

    /**
     * 如果condition不是false则报错
     *
     * @param condition
     * @param msg
     */
    public static void assertFalse(boolean condition, String msg) {
        assertFalse(condition, ResultEnum.ERROR_10005, msg);
    }

    public static void assertFalse(boolean condition, ResultEnum code, String msg) {
        assertFalse(condition, code, LogLevelEnum.INFO, msg);
    }

    public static void assertFalse(boolean condition, ResultEnum code, LogLevelEnum logLevel, String msg) {
        assertTrue(!condition, code, logLevel, msg);
    }

    public static void assertFalse(boolean condition, String format, Object... args) {
        assertFalse(condition, String.format(format, args));
    }

    public static void assertFalse(boolean condition, ResultEnum code, String format, Object... args) {
        assertFalse(condition, code, String.format(format, args));
    }

    /**
     * 字符串非空校验
     *
     * @param str
     * @param msg
     */
    public static void assertNotBlank(String str, String msg) {
        assertNotBlank(str, ResultEnum.ERROR_10005, msg);
    }

    public static void assertNotBlank(String str, ResultEnum code, String msg) {
        assertTrue(!StringUtils.isEmpty(str), code, msg);
    }

    public static void assertNotBlank(String str, String format, Object... args) {
        assertNotBlank(str, ResultEnum.ERROR_10005, format, args);
    }

    public static void assertNotBlank(String str, ResultEnum code, String format, Object... args) {
        assertNotBlank(str, code, String.format(format, args));
    }

    /**
     * 集合非空校验
     *
     * @param collection
     * @param msg
     */
    public static void assertNotEmpty(Collection<?> collection, String msg) {
        assertTrue(!CollectionUtils.isEmpty(collection), msg);
    }

    public static void assertNotEmpty(Collection<?> collection, ResultEnum code, String msg) {
        assertTrue(!CollectionUtils.isEmpty(collection), code, msg);
    }

    public static void assertNotEmpty(Collection<?> collection, String format, Object... args) {
        assertNotEmpty(collection, String.format(format, args));
    }

    public static void assertNotEmpty(Collection<?> collection, ResultEnum code, String format, Object... args) {
        assertNotEmpty(collection, code, String.format(format, args));
    }

    public static void assertNotEmpty(Collection<?> collection, ResultEnum code, LogLevelEnum logLevel, String format, Object... args) {
        assertTrue(!CollectionUtils.isEmpty(collection), code, logLevel, format, args);
    }

    /**
     * map非空校验
     *
     * @param map
     * @param msg
     */
    public static void assertNotEmpty(Map<?, ?> map, String msg) {
        assertTrue(!CollectionUtils.isEmpty(map), msg);
    }

    public static void assertNotEmpty(Map<?, ?> map, String format, Object... args) {
        assertNotEmpty(map, String.format(format, args));
    }

    public static void assertNotEmpty(Object[] objects, String format, Object... args) {
        assertTrue(null != objects && objects.length > 0, String.format(format, args));
    }

    public static void assertEmpty(Collection<?> collection, String msg) {
        assertTrue(CollectionUtils.isEmpty(collection), msg);
    }

    public static void assertEmpty(Collection<?> collection, String format, Object... args) {
        assertEmpty(collection, String.format(format, args));
    }

    /**
     * 传入string 为空，否则报错
     *
     * @param str
     * @param msg
     */
    public static void assertBlank(String str, String msg) {
        assertTrue(StringUtils.isEmpty(str), msg);
    }

    public static void assertBlank(String str, ResultEnum code, String msg) {
        assertTrue(StringUtils.isEmpty(str), code, msg);
    }

    public static void assertBlank(String str, String format, Object... args) {
        assertBlank(str, String.format(format, args));
    }

    /**
     * 检验是否可更新<br>
     * 通过反射获取id与applyId并校验其二者值是否同时为空<br>
     * 若实体不包含id或applyId属性，不可使用此方法<br>
     * id可为Integer或Long；applyId仅可为String
     *
     * @param param 必须包含id与applyId字段，命名也需完全一致
     */
    public static void assertCanApprovalUpdate(Object param) {
        try {
            Field idField = param.getClass().getDeclaredField("id");
            Field applyIdField = param.getClass().getDeclaredField("approvalCode");
            idField.setAccessible(true);
            applyIdField.setAccessible(true);
            assertTrue(idField.get(param) != null || StringUtils.isNotBlank((String) applyIdField.get(param)),
                    "更新时，id与applyId不可同时为空");
        } catch (Exception e) {
        }
    }

}
