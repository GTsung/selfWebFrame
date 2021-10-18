package com.self.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LogLevelEnum {

    INFO("INFO", "level-info"),
    ERROR("ERROR", "level-error"),
    ;

    @Getter
    private String code;

    @Getter
    private String desc;

}
