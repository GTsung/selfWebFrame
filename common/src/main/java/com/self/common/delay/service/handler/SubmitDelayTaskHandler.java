package com.self.common.delay.service.handler;

import com.self.common.delay.enums.DelayTaskTypeEnum;
import com.self.common.delay.service.BaseDelayTaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author GTsung
 * @date 2021/8/20
 */
@Slf4j
@Service
public class SubmitDelayTaskHandler extends BaseDelayTaskHandler {

    public SubmitDelayTaskHandler() {
        super.register(DelayTaskTypeEnum.BUSINESS_SUBMIT.getType(), this);
    }

    @Override
    public void afterExpire(String businessId) {
        System.out.println(String.format("开始业务提交的任务,%s", businessId));
    }
}
