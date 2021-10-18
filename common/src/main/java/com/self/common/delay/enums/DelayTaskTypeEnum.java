package com.self.common.delay.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author GTsung
 * @date 2021/8/20
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DelayTaskTypeEnum {

    BUSINESS_SUBMIT("business_submit", "业务提交", 180),
    WFQ_DATA("wfq_data", "获取数据", 240),
    ;
    @Getter
    private String type;
    @Getter
    private String message;
    // 多长时间后开始执行
    @Getter
    private Integer expireTime;

}
