package com.self.backend.aspect;

import com.self.backend.utils.AnnotationCheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;


/**
 * @author GTsung
 * @date 2022/2/6
 */
@Slf4j
@Aspect
@Component
public class ParamValidateAspect {

    @Autowired
    private Validator validator;

    @Autowired
    private AnnotationCheckUtil annotationCheckUtil;

    @Pointcut("execution(* com.self.backend.controller.ValidateController.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        return annotationCheckUtil.doCheck(validator, joinPoint);
    }

}
