package com.self.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author GTsung
 * @date 2021/8/17
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultEnum {

    SUCCESS("200", "成功"),
    ERROR("501", "失败"),
    OTHER_ERROR("505", "其他错误"),
    ERROR_10005("10005", "未知错误"),
    ERROR_10006("10006", "参数错误"),
    ERROR_20001("20001", "feign错误"),
    ERROR_30000("30000", "NPL"),

    ;



    @Getter
    private String code;
    @Getter
    private String desc;

}
