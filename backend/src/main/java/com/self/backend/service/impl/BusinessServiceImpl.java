package com.self.backend.service.impl;

import com.self.common.delay.enums.DelayTaskTypeEnum;
import com.self.common.delay.service.handler.SubmitDelayTaskHandler;
import com.self.common.delay.service.handler.WfqDataDelayTaskHandler;
import com.self.backend.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author GTsung
 * @date 2021/8/20
 */
@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private WfqDataDelayTaskHandler wfqDataDelayTaskHandler;

    @Autowired
    private SubmitDelayTaskHandler submitDelayTaskHandler;

    @Override
    public void doWfq() {
        String businessId = "wfq";
        wfqDataDelayTaskHandler.start(DelayTaskTypeEnum.WFQ_DATA, businessId);
    }

    @Override
    public void doSubmit() {
        String businessId = "submit";
        submitDelayTaskHandler.start(DelayTaskTypeEnum.BUSINESS_SUBMIT, businessId);
    }
}
