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
public class WfqDataDelayTaskHandler extends BaseDelayTaskHandler {

    public WfqDataDelayTaskHandler() {
        super.register(DelayTaskTypeEnum.WFQ_DATA.getType(), this);
    }

    @Override
    public void afterExpire(String businessId) {
        System.out.println(String.format("开始执行微风起数据获取的任务,%s", businessId));
    }
}
