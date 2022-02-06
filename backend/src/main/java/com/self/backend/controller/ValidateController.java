package com.self.backend.controller;

import com.self.backend.dto.ValidatePhoneDTO;
import com.self.common.anno.SelfValidate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GTsung
 * @date 2022/2/6
 */
@RestController
@RequestMapping("/validate")
public class ValidateController {

    @PostMapping("/phone")
    public Object validate(@RequestBody @SelfValidate ValidatePhoneDTO phoneDTO) {
        return 0;
    }
}
