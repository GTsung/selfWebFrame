package com.self.common.config;

import com.self.common.enums.ResultEnum;
import com.self.common.exception.SelfException;
import com.self.common.utils.JsonUtil;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * 用于打印feign的错误信息
 *
 * @author GTsung
 * @date 2021/9/8
 */
@Slf4j
public class SelfFeignErrorDecoder implements ErrorDecoder {

    private static final String KEY = "message";

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = null;
        try {
            String json = Util.toString(response.body().asReader());
            Map<String, Object> result = JsonUtil.toMap(json);
            log.info("出现异常{}", result);
            if(result.containsKey(KEY) && null != result.get(KEY)) {
                String msg = "" + result.get(KEY);
                exception = new SelfException(ResultEnum.ERROR_20001.getCode(), msg);
            } else {
                exception = new SelfException(ResultEnum.ERROR_30000.getCode(), "Service return NPE");
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return exception;
    }
}
