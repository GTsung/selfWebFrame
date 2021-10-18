package com.self.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DelFlagEnum {

    DELETED(1, "已删除"),
    PERSISTENT(0, "未删除");

    @Getter
    private Integer code;
    @Getter
    private String desc;
}
