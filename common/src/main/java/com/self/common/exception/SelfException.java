package com.self.common.exception;


import com.self.common.enums.LogLevelEnum;
import com.self.common.enums.ResultEnum;
import lombok.Getter;

/**
 * @author GTsung
 * @date 2021/8/17
 */
public class SelfException extends RuntimeException {

    @Getter
    private String code;
    @Getter
    private LogLevelEnum logLevelEnum;

    public SelfException(String message) {
        super(message);
        this.code = ResultEnum.OTHER_ERROR.getCode();
        this.logLevelEnum = LogLevelEnum.INFO;
    }

    public SelfException(String code, String message) {
        super(message);
        this.code = code;
        this.logLevelEnum = LogLevelEnum.INFO;
    }

    public SelfException(String code, String message, LogLevelEnum logLevelEnum) {
        super(message);
        this.code = code;
        this.logLevelEnum = logLevelEnum;
    }


}
