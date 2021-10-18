package com.self.backend.utils;

import com.self.common.res.SelfResult;
import com.self.common.utils.ExceptionUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = Exception.class)
    public SelfResult handlerException(Exception e) {
        return ExceptionUtil.dealException(e);
    }
}
