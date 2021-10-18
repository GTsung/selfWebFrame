package com.self.common.delay.service;

import com.self.common.delay.enums.DelayTaskTypeEnum;

/**
 * 延时任务接口
 */
public interface DelayTaskService {

    /**
     * 初始化
     * @param taskTypeEnum
     * @param businessId
     */
    void start(DelayTaskTypeEnum taskTypeEnum, String businessId);

    /**
     * 延时结束后，需要处理的业务方法
     */
    void afterExpire(String businessId);

}
