package com.self.common.utils;

import com.self.common.enums.ResultEnum;
import com.self.common.exception.SelfException;
import com.self.common.res.SelfResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@Slf4j
public class ExceptionUtil {
    public static SelfResult dealException(Exception e) {
        log.info("出现异常{}", e.getMessage(), e);
        SelfResult res = null;
        if (e instanceof UndeclaredThrowableException) {
            // Aspect中抛出的Exception会被转为UndeclaredThrowableException
            if (((UndeclaredThrowableException) e).getUndeclaredThrowable() instanceof SelfException) {
                res = handleException((SelfException) ((UndeclaredThrowableException) e).getUndeclaredThrowable());
            } else {
                res = handleException(e);
            }
        } else {
            res = handleException(e);
        }
        return res;
    }

    private static SelfResult handleException(Exception e) {
        SelfResult res = new SelfResult();
        if (e instanceof SelfException) {
            SelfException exception = (SelfException) e;
            res.setCode(exception.getCode());
            res.setData(exception.getMessage());
        } else if (e instanceof MethodArgumentNotValidException || e instanceof BindException) {
            res.setCode(ResultEnum.ERROR_10006.getCode());
            res.setData(getValidResult(e));
        } else {
            res.setCode(ResultEnum.ERROR_10005.getCode());
            res.setData("服务器开小差了，请稍后重试~");
        }
        return res;
    }

    /**
     * 通过反射获取校验结果，并封装报错信息
     *
     * @param e
     * @return
     */
    private static StringBuffer getValidResult(Exception e) {
        StringBuffer msg = new StringBuffer();
        if (!(e instanceof MethodArgumentNotValidException) && !(e instanceof BindException)) {
            return msg;
        }
        try {
            Method getResultMethod = e.getClass().getMethod("getBindingResult");
            BindingResult validResult = (BindingResult) getResultMethod.invoke(e);
            validResult.getAllErrors().forEach(item -> {
                msg.append(item.getDefaultMessage() + "；");
            });
        } catch (Exception e1) {
            // 此处拦截异常并输出日志，若异常抛出，会使异常从参数校验失败变为反射异常
            log.error("DefaultExceptionHandlerAdvice.getValidResult获取信息校验参数信息失败-----e1:",e1);
        }
        return msg;
    }
}
