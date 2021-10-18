package com.self.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Map;

/**
 * @author GTsung
 * @date 2021/9/18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpressionUtil {

    public static Object expressionParse(Map<String, Object> maps, String expression) {
        JexlContext jexlContext = new MapContext();
        maps.forEach(jexlContext::set);
        JexlEngine jexlEngine = new Engine();
        JexlExpression jexlExpression = jexlEngine.createExpression(expression);
        return jexlExpression.evaluate(jexlContext);
    }

}
