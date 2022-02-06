package com.self.common.aspect;

import com.self.common.exception.SelfException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author GTsung
 * @date 2022/2/6
 */
@Slf4j
public class BaseAspect {

    public Object process(ProceedingJoinPoint joinPoint) {
        return process(joinPoint, null);
    }

    public Object process(ProceedingJoinPoint joinPoint, Object[] args) {
        Object proceed = null;

        try {
            if (args == null || args.length < 1) {
                proceed = joinPoint.proceed();
            } else {
                proceed = joinPoint.proceed(args);
            }
        }catch (Exception e) {
            throw new SelfException(e.getMessage());
        } catch (Throwable t) {
            throw new SelfException(t.getMessage());
        }
        return proceed;
    }
}
