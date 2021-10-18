package com.self.backend.aspect;

import com.self.common.utils.AspectUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Slf4j
@Aspect
@Component
public class ResultDealAspect {

    // controller包中的而且是带有@OperationResult这个注解的
    @Pointcut("execution(* com.self.backend.controller.*.*(..)) " +
            "&& (@annotation(com.self.common.anno.OperationResult) || @annotation(com.self.common.anno.PageAnn))")
    public void point() {}

    @Around("point()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        return AspectUtil.dealResult(jp);
    }

}
