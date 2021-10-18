package com.self.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分页处理
 * 返回结果类型为{@link com.self.common.res.SelfResult}
 * 处理类{@link com.self.common.aspect.ResultDealAspect}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageAnn {

    /**
     * 是否需要打印参数日志
     * @return
     */
    boolean printParamLog() default true;

    /**
     * 打印结果
     * @return
     */
    boolean printResLog() default true;
}
