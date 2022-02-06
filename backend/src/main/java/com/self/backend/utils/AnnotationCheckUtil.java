package com.self.backend.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.self.common.anno.SelfValidate;
import com.self.common.aspect.BaseAspect;
import com.self.common.enums.ResultEnum;
import com.self.common.exception.SelfException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author GTsung
 * @date 2022/2/6
 */
@Slf4j
@Component
public class AnnotationCheckUtil extends BaseAspect {


    public Object doCheck(Validator validator, ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null) {
            return process(joinPoint);
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        doValidate(args, method, validator, joinPoint.getTarget().getClass().getName());
        return process(joinPoint, args);
    }

    private void doValidate(Object[] args, Method method, Validator validator, String className) {
        if (args == null || args.length == 0) return;
        List<Object> toValidateArgs = getValidateArgs(args, method);

        Set<ConstraintViolation> validateInfoSet = Sets.newHashSet();
        for (Object obj : toValidateArgs) {
            // 加入校驗信息
            Set<ConstraintViolation<Object>> validateInfo = validator.validate(obj);
            if (!CollectionUtils.isEmpty(validateInfo)) {
                validateInfoSet.addAll(validateInfo);
            }
        }

        if (CollectionUtils.isEmpty(validateInfoSet)) {
            return;
        }

        String errorMsg = validateInfoSet
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());
        throw new SelfException(ResultEnum.ERROR_10006.getCode(), errorMsg);
    }

    private List<Object> getValidateArgs(Object[] args, Method method) {
        List<Object> result = Lists.newArrayList();
        // 獲取多個參數的多個注解，二維數組
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return result;
        }
        // parameterAnnotations.length相當於遍歷多個參數
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            if (annotations == null || annotations.length == 0) continue;
            for (Annotation annotation : annotations) {
                if (annotation instanceof SelfValidate) {
                    result.add(args[i]);
                    break;
                }
            }
        }
        return result;
    }


}
