package com.self.common.utils;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GTsung
 * @date 2021/9/18
 */
@SpringBootTest
public class ExpressionUtilTest {

    @Test
    public void test1() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "12");
        String expression = "key eq 12";
        assert "true".equals(ExpressionUtil.expressionParse(map, expression).toString());
    }

}
