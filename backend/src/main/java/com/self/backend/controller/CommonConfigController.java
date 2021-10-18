package com.self.backend.controller;

import com.self.common.anno.OperationResult;
import com.self.backend.domain.CommonConfigDO;
import com.self.backend.service.CommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author GTsung
 * @date 2021/8/19
 */
@RestController
@RequestMapping("common/config")
public class CommonConfigController {

    @Autowired
    private CommonConfigService commonConfigService;

    @PostMapping
    @OperationResult
    public Object save(@RequestBody CommonConfigDO commonConfigDO) {
        return commonConfigService.save(commonConfigDO);
    }

    @GetMapping("/group/{group}")
    @OperationResult
    public Object selectByGroup(@PathVariable("group") String group) {
        return commonConfigService.selectByGroup(group);
    }

    @OperationResult
    @GetMapping("groupAndKey/{group}/{key}")
    public Object selectByGroupAndKey(@PathVariable("group") String group, @PathVariable("key")String key) {
        return commonConfigService.selectByGroupAndKey(group, key);
    }

    @OperationResult
    @GetMapping("key/{key}")
    public Object selectByKey(@PathVariable("key")String key) {
        return commonConfigService.selectByKey(key);
    }

    @PutMapping
    @OperationResult
    public Object update(@RequestBody CommonConfigDO commonConfigDO) {
        return commonConfigService.update(commonConfigDO);
    }

}
